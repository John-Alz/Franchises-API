package com.api.franchises.application.config;

import com.api.franchises.domain.api.FranchiseServicePort;
import com.api.franchises.domain.spi.FranchisePersistencePort;
import com.api.franchises.domain.usecase.FranchiseUseCase;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.FranchisePersistenceAdapter;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.mapper.FranchiseEntityMapper;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FranchiseConfig {

    private final FranchiseRepository franchiseRepository;
    private final FranchiseEntityMapper franchiseEntityMapper;

    @Bean
    public FranchisePersistencePort franchisePersistencePort() {
        return new FranchisePersistenceAdapter(franchiseRepository, franchiseEntityMapper);
    }

    @Bean
    public FranchiseServicePort franchiseServicePort(FranchisePersistencePort franchisePersistencePort) {
        return new FranchiseUseCase(franchisePersistencePort);
    }


}
