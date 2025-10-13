package com.api.franchises.infrastructure.entrypoints.mapper;

import com.api.franchises.domain.model.TopProductPerBranch;
import com.api.franchises.infrastructure.entrypoints.dto.TopProductPerBranchResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReportingMapper {

    @Mapping(target = "product.productId",   source = "productId")
    @Mapping(target = "product.productName", source = "productName")
    @Mapping(target = "product.stock",       source = "stock")
    TopProductPerBranchResponse toResponse(TopProductPerBranch topProductPerBranch);

}
