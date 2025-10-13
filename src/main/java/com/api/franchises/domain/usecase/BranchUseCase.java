package com.api.franchises.domain.usecase;

import com.api.franchises.domain.api.BranchServicePort;
import com.api.franchises.domain.enums.TechnicalMessage;
import com.api.franchises.domain.exceptions.BusinessException;
import com.api.franchises.domain.model.Branch;
import com.api.franchises.domain.spi.BranchPersistencePort;
import com.api.franchises.domain.spi.FranchisePersistencePort;
import reactor.core.publisher.Mono;

public class BranchUseCase implements BranchServicePort {

    private final BranchPersistencePort branchPersistencePort;
    private final FranchisePersistencePort franchisePersistencePort;

    public BranchUseCase(BranchPersistencePort branchPersistencePort, FranchisePersistencePort franchisePersistencePort) {
        this.branchPersistencePort = branchPersistencePort;
        this.franchisePersistencePort = franchisePersistencePort;
    }

    @Override
    public Mono<Branch> saveBranch(Long franchiseId, Branch branch, String messageId) {
        return franchisePersistencePort.existById(franchiseId)
                .filter(exist -> exist)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.FRANCHISE_NOT_FOUND)))
                .flatMap(ignore -> branchPersistencePort.saveBranch(franchiseId, new Branch(
                        null,
                        branch.name(),
                        franchiseId
                )));
    }

    @Override
    public Mono<Void> updateNameBranch(Long franchiseId, Long branchId, String newName) {
        return franchisePersistencePort.existById(franchiseId)
                .flatMap(exists -> exists
                ? franchisePersistencePort.updateNameFranchise(franchiseId, branchId, newName)
                : Mono.error(new BusinessException(TechnicalMessage.FRANCHISE_NOT_FOUND)))
                .flatMap(rows -> rows > 0
                ? Mono.<Void>empty()
                : Mono.error(new BusinessException(TechnicalMessage.BRANCH_NOT_IN_FRANCHISE_OR_NOT_FOUND)));
    }
}
