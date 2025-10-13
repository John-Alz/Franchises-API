package com.api.franchises.infrastructure.entrypoints.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ProductDTO {

    private String name;
    private int stock;
}
