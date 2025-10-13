package com.api.franchises.infrastructure.entrypoints.mapper;

import com.api.franchises.domain.model.Branch;
import com.api.franchises.domain.model.Franchise;
import com.api.franchises.infrastructure.entrypoints.dto.BranchDTO;
import com.api.franchises.infrastructure.entrypoints.dto.FranchiseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BranchMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "franchiseId", ignore = true)
    Branch branchDTOToBranch(BranchDTO branchDTO);


}
