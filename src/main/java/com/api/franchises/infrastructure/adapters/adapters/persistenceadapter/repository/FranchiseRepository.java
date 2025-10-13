package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.repository;

import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.entity.FranchiseEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface FranchiseRepository extends ReactiveCrudRepository<FranchiseEntity, Long> {

    Mono<Boolean> existsByName(String name);

    @Modifying
    @Query("UPDATE franchises SET name = :newName WHERE id = :franchiseId")
    Mono<Integer> updateNameFranchise(Long franchiseId, String newName);
}

