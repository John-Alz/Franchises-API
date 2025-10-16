package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.mapper;

import com.api.franchises.domain.model.TopProductPerBranch;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.dto.TopProductPerBranchRow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ReportingDtoMapper {

    @Mappings({
            @Mapping(source = "branchid", target = "branchId"),
            @Mapping(source = "branchname", target = "branchName"),
            @Mapping(source = "productid", target = "productId"),
            @Mapping(source = "productname", target = "productName")
    })
    TopProductPerBranch toModel(TopProductPerBranchRow topProductPerBranchRow);

}
