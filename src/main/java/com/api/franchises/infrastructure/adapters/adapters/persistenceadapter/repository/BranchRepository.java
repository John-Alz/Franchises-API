package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.repository;

import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.entity.BranchEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BranchRepository extends ReactiveCrudRepository<BranchEntity, Long> {

    @Modifying
    @Query("UPDATE branches SET name = :newName WHERE id = :branchId AND franchise_id = :franchiseId")
    Mono<Integer> updateNameBranch(Long franchiseId, Long branchId, String newName);
}
