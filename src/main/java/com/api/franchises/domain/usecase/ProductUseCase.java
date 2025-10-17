package com.api.franchises.domain.usecase;

import com.api.franchises.domain.api.ProductServicePort;
import com.api.franchises.domain.constants.Constants;
import com.api.franchises.domain.enums.TechnicalMessage;
import com.api.franchises.domain.exceptions.BusinessException;
import com.api.franchises.domain.model.Product;
import com.api.franchises.domain.spi.BranchPersistencePort;
import com.api.franchises.domain.spi.ProductPersistencePort;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

public class ProductUseCase implements ProductServicePort  {

    private final ProductPersistencePort productPersistencePort;
    private final BranchPersistencePort branchPersistencePort;

    public ProductUseCase(ProductPersistencePort productPersistencePort, BranchPersistencePort branchPersistencePort) {
        this.productPersistencePort = productPersistencePort;
        this.branchPersistencePort = branchPersistencePort;
    }

    @Override
    public Mono<Product> saveProduct(Long branchId, Product product, String messageId) {
        return validateBranchExists(branchId)
                .then(Mono.defer(() -> productPersistencePort.saveProduct(
                        new Product(null, product.name(), product.stock(), branchId)
                )));
    }

    @Override
    public Mono<Void> deleteProduct(Long branchId, Long productId, String messageId) {
        return executeProductModification(branchId, () -> productPersistencePort.deleteProduct(branchId, productId));
    }

    @Override
    public Mono<Void> updateStockProduct(Long branchId, Long productId, int newStock) {
        if (newStock < Constants.MIN_VALUE) {
            return Mono.error(new BusinessException(TechnicalMessage.NEGATIVE_STOCK));
        }
        return executeProductModification(branchId, () -> productPersistencePort.updateStockProduct(branchId, productId, newStock));
    }

    @Override
    public Mono<Void> updateNameProduct(Long branchId, Long productId, String name) {
        return executeProductModification(branchId, () -> productPersistencePort.updateNameProduct(branchId, productId, name));
    }


    private Mono<Void> validateBranchExists(Long branchId) {
        return branchPersistencePort.existById(branchId)
                .filter(exist -> exist)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.BRANCH_NOT_FOUND)))
                .then();
    }

    private Mono<Void> executeProductModification(Long branchId,  Supplier<Mono<Integer>> operationSupplier) {
        return validateBranchExists(branchId)
                .then(Mono.defer(operationSupplier))
                .filter(rowsAffected -> rowsAffected > Constants.MIN_VALUE)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.PRODUCT_NOT_IN_BRANCH_OR_NOT_FOUND)))
                .then();
    }

}
