package com.api.franchises.application.config;

import com.api.franchises.domain.api.ReportingServicePort;
import com.api.franchises.domain.spi.FranchisePersistencePort;
import com.api.franchises.domain.spi.ReportingPersistencePort;
import com.api.franchises.domain.usecase.ReportingUseCase;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.ReportingPersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.core.DatabaseClient;

@Configuration
@RequiredArgsConstructor
public class ReportingConfig {

    private final DatabaseClient db;

    @Bean
    public ReportingPersistencePort reportingPersistencePort() {
        return new ReportingPersistenceAdapter(db);
    }

    @Bean
    public ReportingServicePort reportingServicePort(ReportingPersistencePort reportingPersistencePort, FranchisePersistencePort franchisePersistencePort) {
        return new ReportingUseCase(reportingPersistencePort, franchisePersistencePort);
    }

}
