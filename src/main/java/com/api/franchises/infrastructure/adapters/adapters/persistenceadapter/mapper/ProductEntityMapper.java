package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.mapper;

import com.api.franchises.domain.model.Product;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductEntityMapper {

    Product toModel(ProductEntity productEntity);
    ProductEntity toEntity(Product product);

}
