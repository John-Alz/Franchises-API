package com.api.franchises.infrastructure.entrypoints.mapper;

import com.api.franchises.domain.model.Branch;
import com.api.franchises.domain.model.Franchise;
import com.api.franchises.infrastructure.entrypoints.dto.BranchDTO;
import com.api.franchises.infrastructure.entrypoints.dto.FranchiseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BranchMapper {

    Branch branchDTOToBranch(BranchDTO branchDTO);


}
