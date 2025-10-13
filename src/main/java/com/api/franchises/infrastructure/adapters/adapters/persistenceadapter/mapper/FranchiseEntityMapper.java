package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.mapper;

import com.api.franchises.domain.model.Franchise;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.entity.FranchiseEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FranchiseEntityMapper {

    Franchise toModel(FranchiseEntity franchiseEntity);
    FranchiseEntity toEntity(Franchise franchise);
}
