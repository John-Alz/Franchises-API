package com.api.franchises.infrastructure.entrypoints.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@Schema(name = "Product", description = "Product data")
public class ProductDTO {

    @NotBlank
    @Schema(example = "Pepperoni pizza", description = "Product name")
    private String name;

    @NotNull
    @Schema(example = "10", description = "Product stock")
    private Integer stock;
}

