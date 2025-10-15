package com.api.franchises.infrastructure.entrypoints.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@Schema(name = "UpdateNameRequest", description = "Petición para actualizar el nombre de la franquicia, sucursal y producto")
public class UpdateNameRequest {

    @NotBlank
    @Schema(example = "Nuevo Nombre", description = "Nuevo nombre para la franquicia, sucursal y producto")
    private String newName;

}
