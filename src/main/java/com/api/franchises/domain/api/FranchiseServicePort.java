package com.api.franchises.domain.api;

import com.api.franchises.domain.model.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseServicePort {

    Mono<Franchise> saveFranchise(Franchise franchise, String messageId);

}
