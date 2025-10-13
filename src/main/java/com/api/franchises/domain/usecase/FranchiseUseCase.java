package com.api.franchises.domain.usecase;

import com.api.franchises.domain.api.FranchiseServicePort;
import com.api.franchises.domain.enums.TechnicalMessage;
import com.api.franchises.domain.exceptions.BusinessException;
import com.api.franchises.domain.model.Franchise;
import com.api.franchises.domain.spi.FranchisePersistencePort;
import reactor.core.publisher.Mono;

public class FranchiseUseCase implements FranchiseServicePort {

    private final FranchisePersistencePort franchisePersistencePort;

    public FranchiseUseCase(FranchisePersistencePort franchisePersistencePort) {
        this.franchisePersistencePort = franchisePersistencePort;
    }

    @Override
    public Mono<Franchise> saveFranchise(Franchise franchise, String messageId) {
        return franchisePersistencePort.existByName(franchise.name())
                .filter(exist -> !exist)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.FRANCHISE_ALREADY_EXISTS)))
                .flatMap(ignore -> franchisePersistencePort.saveFranchise(franchise));
    }
}
