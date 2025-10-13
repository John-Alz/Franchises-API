package com.api.franchises.domain.api;

import com.api.franchises.domain.model.Branch;
import reactor.core.publisher.Mono;

public interface BranchServicePort {

    Mono<Branch> saveBranch(Branch branch);

}
