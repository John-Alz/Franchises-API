package com.api.franchises.infrastructure.entrypoints.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@Schema(name = "ProductSummary", description = "Top product summary")
public class ProductSummary {

    @Schema(description = "Product ID", example = "1001")
    private Long productId;
    @Schema(description = "Product name", example = "Neapolitan Pizza")
    private String productName;
    @Schema(description = "Available stock", example = "47")
    private Integer stock;
}
