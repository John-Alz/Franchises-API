package com.api.franchises.infrastructure.entrypoints.mapper;

import com.api.franchises.domain.model.Franchise;
import com.api.franchises.infrastructure.entrypoints.dto.FranchiseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FranchiseMapper {

    Franchise franchiseDTOToFranchise(FranchiseDTO franchiseDTO);
}
