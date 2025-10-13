package com.api.franchises.infrastructure.entrypoints.mapper;

import com.api.franchises.domain.model.Franchise;
import com.api.franchises.infrastructure.entrypoints.dto.FranchiseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FranchiseMapper {

    @Mapping(target = "id", ignore = true)
    Franchise franchiseDTOToFranchise(FranchiseDTO franchiseDTO);
}
