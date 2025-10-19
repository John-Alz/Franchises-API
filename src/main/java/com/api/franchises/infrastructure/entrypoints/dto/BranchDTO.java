package com.api.franchises.infrastructure.entrypoints.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@Schema(name = "Branch", description = "Branch data")
public class BranchDTO {

    @NotBlank
    @Schema(example = "North branch", description = "Branch name")
    private String name;

}
