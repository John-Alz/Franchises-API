package com.api.franchises.infrastructure.entrypoints.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(name = "UpdateStockRequest", description = "Request to update the product stock")
public class UpdateStockRequest {

    @NotBlank
    @Schema(example = "New Stock", description = "New stock for the product")
    private int stock;

}
