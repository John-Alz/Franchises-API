package com.api.franchises.domain.spi;

import com.api.franchises.domain.model.Product;
import reactor.core.publisher.Mono;

public interface ProductPersistencePort {

    Mono<Product> saveProduct(Product product);
    Mono<Integer> deleteProduct(Long branchId, Long productId);


}
