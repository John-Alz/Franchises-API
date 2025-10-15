package com.api.franchises.infrastructure.entrypoints.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(name = "UpdateStockRequest", description = "Petición para actualizar el stock del producto")
public class UpdateStockRequest {

    @NotBlank
    @Schema(example = "Nuevo Stock", description = "Nuevo stock para el producto")
    private int stock;

}
