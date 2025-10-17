package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.mapper;

import com.api.franchises.domain.model.Branch;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.entity.BranchEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BranchEntityMapper {

    Branch toModel(BranchEntity branchEntity);
    BranchEntity toEntity(Branch branch);

}
