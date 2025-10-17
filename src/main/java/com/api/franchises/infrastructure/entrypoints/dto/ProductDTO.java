package com.api.franchises.infrastructure.entrypoints.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@Schema(name = "Product", description = "Product data")
public class ProductDTO {

    @NotBlank
    @Schema(example = "Pepperoni pizza", description = "Product name")
    private String name;

    @NotBlank
    @Schema(example = "10", description = "Product stock")
    private int stock;
}

