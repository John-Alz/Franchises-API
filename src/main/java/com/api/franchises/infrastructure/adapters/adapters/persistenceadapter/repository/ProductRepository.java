package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.repository;

import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, Long> {

    @Modifying
    @Query("DELETE FROM products WHERE id = :id AND branch_id = :branchId")
    Mono<Integer> deleteByIdAndBranchId(Long id, Long branchId);
}
