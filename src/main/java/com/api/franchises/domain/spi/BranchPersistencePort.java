package com.api.franchises.domain.spi;

import com.api.franchises.domain.model.Branch;
import reactor.core.publisher.Mono;

public interface BranchPersistencePort {

    Mono<Branch> saveBranch(Long franchiseId, Branch branch);

}
