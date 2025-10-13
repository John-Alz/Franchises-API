package com.api.franchises.domain.spi;

import com.api.franchises.domain.model.Franchise;
import reactor.core.publisher.Mono;

public interface FranchisePersistencePort {

    Mono<Franchise> saveFranchise(Franchise franchise);
    Mono<Boolean> existByName(String name);
    Mono<Boolean> existById(Long id);
    Mono<Integer> updateNameFranchise(Long franchiseId, String newName);


}
