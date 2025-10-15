package com.api.franchises.infrastructure.entrypoints.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@Schema(name = "ProductSummary", description = "Resumen del producto top")
public class ProductSummary {

    @Schema(description = "ID del producto", example = "1001")
    private Long productId;
    @Schema(description = "Nombre del producto", example = "Pizza Napolitana")
    private String productName;
    @Schema(description = "Stock disponible", example = "47")
    private Integer stock;
}
