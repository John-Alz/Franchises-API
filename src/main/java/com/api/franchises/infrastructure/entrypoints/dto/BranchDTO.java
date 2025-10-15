package com.api.franchises.infrastructure.entrypoints.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@Schema(name = "Branch", description = "Datos de la sucursal")
public class BranchDTO {

    @Schema(example = "Sucursal norte", description = "Nombre de la sucursal")
    private String name;

}
