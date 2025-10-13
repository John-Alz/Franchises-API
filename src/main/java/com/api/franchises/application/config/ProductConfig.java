package com.api.franchises.application.config;

import com.api.franchises.domain.api.ProductServicePort;
import com.api.franchises.domain.spi.BranchPersistencePort;
import com.api.franchises.domain.spi.ProductPersistencePort;
import com.api.franchises.domain.usecase.ProductUseCase;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.ProductPersistenceAdapter;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.mapper.ProductEntityMapper;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ProductConfig {

    private final ProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;

    @Bean
    public ProductPersistencePort productPersistencePort() {
        return new ProductPersistenceAdapter(productRepository, productEntityMapper);
    }

    @Bean
    public ProductServicePort productServicePort(ProductPersistencePort productPersistencePort, BranchPersistencePort branchPersistencePort) {
        return new ProductUseCase(productPersistencePort, branchPersistencePort);
    }

}
