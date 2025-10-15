package com.api.franchises.infrastructure.entrypoints.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@Schema(name = "Franchise", description = "Datos de la franquicia")
public class FranchiseDTO {

    @NotBlank
    @Schema(example = "Pizza Planet", description = "Nombre de la franquicia")
    private String name;

}
