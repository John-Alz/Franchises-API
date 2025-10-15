package com.api.franchises.infrastructure.entrypoints.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@Schema(name = "TopProductPerBranchResponse", description = "Producto top por sucursal")
public class TopProductPerBranchResponse {

    @Schema(description = "ID de la sucursal", example = "10")
    private Long branchId;
    @Schema(description = "Nombre de la sucursal", example = "Sucursal Centro")
    private String branchName;

    @Schema(description = "Producto que ocupa el primer lugar en la sucursal")
    private ProductSummary product;

}
