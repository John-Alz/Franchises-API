package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter;

import com.api.franchises.domain.model.Product;
import com.api.franchises.domain.spi.ProductPersistencePort;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.mapper.ProductEntityMapper;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductPersistencePort {

    private final ProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;

    @Override
    public Mono<Product> saveProduct(Product product) {
        return productRepository.save(productEntityMapper.toEntity(product))
                .map(productEntityMapper::toModel);
    }

    @Override
    public Mono<Integer> deleteProduct(Long branchId, Long productId) {
        return productRepository.deleteByIdAndBranchId(productId, branchId)
                .doOnNext(count -> log.info("ROWS ADAPTER: {}", count));
    }

    @Override
    public Mono<Integer> updateStockProduct(Long branchId, Long productId, int newStock) {
        return productRepository.updateStockInBranch(branchId, productId, newStock);
    }

    @Override
    public Mono<Integer> updateNameProduct(Long branchId, Long productId, String name) {
        return productRepository.updateNameInBranch(branchId, productId, name);
    }

}
