package com.api.franchises.domain.usecase;

import com.api.franchises.domain.api.ProductServicePort;
import com.api.franchises.domain.enums.TechnicalMessage;
import com.api.franchises.domain.exceptions.BusinessException;
import com.api.franchises.domain.model.Branch;
import com.api.franchises.domain.model.Product;
import com.api.franchises.domain.spi.BranchPersistencePort;
import com.api.franchises.domain.spi.ProductPersistencePort;
import reactor.core.publisher.Mono;

public class ProductUseCase implements ProductServicePort  {

    private final ProductPersistencePort productPersistencePort;
    private final BranchPersistencePort branchPersistencePort;

    public ProductUseCase(ProductPersistencePort productPersistencePort, BranchPersistencePort branchPersistencePort) {
        this.productPersistencePort = productPersistencePort;
        this.branchPersistencePort = branchPersistencePort;
    }

    @Override
    public Mono<Product> saveProduct(Long branchId, Product product, String messageId) {
        return branchPersistencePort.existById(branchId)
                .filter(exist -> exist)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.BRANCH_NOT_FOUND)))
                .flatMap(ignore -> productPersistencePort.saveProduct(new Product(
                        null,
                        product.name(),
                        product.stock(),
                        branchId
                )));
    }

    @Override
    public Mono<Void> deleteProduct(Long branchId, Long productId, String messageId) {
        return branchPersistencePort.existById(branchId)
                .flatMap(exist -> exist
                ? productPersistencePort.deleteProduct(branchId, productId)
                : Mono.error(new BusinessException(TechnicalMessage.BRANCH_NOT_FOUND)))
                .flatMap(rows -> rows > 0
                ? Mono.<Void>empty()
                : Mono.error(new BusinessException(TechnicalMessage.PRODUCT_NOT_IN_BRANCH_OR_NOT_FOUND)));
    }

    @Override
    public Mono<Void> updateStockProduct(Long branchId, Long productId, int newStock) {
        return branchPersistencePort.existById(branchId)
                .flatMap(exist -> exist
                ? productPersistencePort.updateStockProduct(branchId, productId, newStock)
                : Mono.error(new BusinessException(TechnicalMessage.BRANCH_NOT_FOUND)))
                .flatMap(rows -> rows > 0
                        ? Mono.<Void>empty()
                        : Mono.error(new BusinessException(TechnicalMessage.PRODUCT_NOT_IN_BRANCH_OR_NOT_FOUND)));
    }


}
