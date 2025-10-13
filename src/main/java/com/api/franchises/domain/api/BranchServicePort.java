package com.api.franchises.domain.api;

import com.api.franchises.domain.model.Branch;
import reactor.core.publisher.Mono;

public interface BranchServicePort {

    Mono<Branch> saveBranch(Long franchiseId, Branch branch, String messageId);
    Mono<Void> updateNameBranch(Long franchiseId, Long branchId, String newName);

}
