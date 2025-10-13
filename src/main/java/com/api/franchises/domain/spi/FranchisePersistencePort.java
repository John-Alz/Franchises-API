package com.api.franchises.domain.spi;

import com.api.franchises.domain.model.Franchise;
import reactor.core.publisher.Mono;

public interface FranchisePersistencePort {

    Mono<Franchise> saveFranchise(Franchise franchise);

}
