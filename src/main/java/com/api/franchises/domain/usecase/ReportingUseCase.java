package com.api.franchises.domain.usecase;

import com.api.franchises.domain.api.ReportingServicePort;
import com.api.franchises.domain.enums.TechnicalMessage;
import com.api.franchises.domain.exceptions.BusinessException;
import com.api.franchises.domain.model.TopProductPerBranch;
import com.api.franchises.domain.spi.FranchisePersistencePort;
import com.api.franchises.domain.spi.ReportingPersistencePort;
import reactor.core.publisher.Flux;

public class ReportingUseCase implements ReportingServicePort {

    private final ReportingPersistencePort reportingPersistencePort;
    private final FranchisePersistencePort franchisePersistencePort;

    public ReportingUseCase(ReportingPersistencePort reportingPersistencePort, FranchisePersistencePort franchisePersistencePort) {
        this.reportingPersistencePort = reportingPersistencePort;
        this.franchisePersistencePort = franchisePersistencePort;
    }

    @Override
    public Flux<TopProductPerBranch> topProductPerBranch(Long franchiseId) {
        return franchisePersistencePort.existById(franchiseId)
                .flatMapMany(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return reportingPersistencePort.topProductPerBranch(franchiseId);
                    }
                    return Flux.error(new BusinessException(TechnicalMessage.FRANCHISE_NOT_FOUND));
                });
    }
}
