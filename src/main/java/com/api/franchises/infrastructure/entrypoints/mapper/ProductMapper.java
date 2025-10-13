package com.api.franchises.infrastructure.entrypoints.mapper;

import com.api.franchises.domain.model.Franchise;
import com.api.franchises.domain.model.Product;
import com.api.franchises.infrastructure.entrypoints.dto.FranchiseDTO;
import com.api.franchises.infrastructure.entrypoints.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "branchId", ignore = true)
    Product productDTOToProduct(ProductDTO productDTO);


}
