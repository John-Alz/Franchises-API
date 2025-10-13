package com.api.franchises.application.config;

import com.api.franchises.domain.api.BranchServicePort;
import com.api.franchises.domain.spi.BranchPersistencePort;
import com.api.franchises.domain.spi.FranchisePersistencePort;
import com.api.franchises.domain.usecase.BranchUseCase;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.BranchPersistenceAdapter;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.mapper.BranchEntityMapper;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BranchConfig {

    private final BranchRepository branchRepository;
    private final BranchEntityMapper branchEntityMapper;

    @Bean
    public BranchPersistencePort branchPersistencePort() {
        return new BranchPersistenceAdapter(branchRepository, branchEntityMapper);
    }

    @Bean
    public BranchServicePort branchServicePort(BranchPersistencePort branchPersistencePort, FranchisePersistencePort franchisePersistencePort) {
        return new BranchUseCase(branchPersistencePort, franchisePersistencePort);
    }

}
