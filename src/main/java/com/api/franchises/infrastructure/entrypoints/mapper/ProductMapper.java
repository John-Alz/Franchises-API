package com.api.franchises.infrastructure.entrypoints.mapper;

import com.api.franchises.domain.model.Franchise;
import com.api.franchises.domain.model.Product;
import com.api.franchises.infrastructure.entrypoints.dto.FranchiseDTO;
import com.api.franchises.infrastructure.entrypoints.dto.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product productDTOToProduct(ProductDTO productDTO);


}
