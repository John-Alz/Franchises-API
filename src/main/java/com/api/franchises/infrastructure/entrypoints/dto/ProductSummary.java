package com.api.franchises.infrastructure.entrypoints.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ProductSummary {
    private Long productId;
    private String productName;
    private Integer stock;
}
