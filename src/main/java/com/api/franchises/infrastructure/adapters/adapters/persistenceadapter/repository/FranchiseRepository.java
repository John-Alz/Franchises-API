package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.repository;

import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.entity.FranchiseEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface FranchiseRepository extends ReactiveMongoRepository<FranchiseEntity, String> {

    Mono<Boolean> existsByName(String name);

}
