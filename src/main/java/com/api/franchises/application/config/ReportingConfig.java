package com.api.franchises.application.config;

import com.api.franchises.domain.api.ReportingServicePort;
import com.api.franchises.domain.spi.FranchisePersistencePort;
import com.api.franchises.domain.spi.ReportingPersistencePort;
import com.api.franchises.domain.usecase.ReportingUseCase;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.ReportingPersistenceAdapter;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.mapper.ReportingDtoMapper;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.repository.ReportingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ReportingConfig {

    private final ReportingRepository repository;
    private final ReportingDtoMapper mapper;

    @Bean
    public ReportingPersistencePort reportingPersistencePort() {
        return new ReportingPersistenceAdapter(repository, mapper);
    }

    @Bean
    public ReportingServicePort reportingServicePort(ReportingPersistencePort reportingPersistencePort, FranchisePersistencePort franchisePersistencePort) {
        return new ReportingUseCase(reportingPersistencePort, franchisePersistencePort);
    }

}
