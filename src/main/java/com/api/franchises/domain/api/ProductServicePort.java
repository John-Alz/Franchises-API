package com.api.franchises.domain.api;

import com.api.franchises.domain.model.Product;
import reactor.core.publisher.Mono;

public interface ProductServicePort {

    Mono<Product> saveProduct(Long branchId, Product product, String messageId);
    Mono<Void> deleteProduct(Long branchId, Long productId, String messageId);
}
