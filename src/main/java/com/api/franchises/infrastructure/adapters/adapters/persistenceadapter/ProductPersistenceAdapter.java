package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter;

import com.api.franchises.domain.model.Product;
import com.api.franchises.domain.spi.ProductPersistencePort;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.mapper.ProductEntityMapper;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductPersistencePort {

    private final ProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;

    @Override
    public Mono<Product> saveProduct(Product product) {
        return productRepository.save(productEntityMapper.toEntity(product))
                .map(productEntityMapper::toModel);
    }
}
