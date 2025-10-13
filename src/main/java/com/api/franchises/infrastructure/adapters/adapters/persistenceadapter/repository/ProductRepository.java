package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.repository;

import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.entity.ProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, Long> {
}
