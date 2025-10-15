package com.api.franchises.infrastructure.entrypoints.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@Schema(name = "UpdateNameRequest", description = "Request to update the name of the franchise, branch, and product")
public class UpdateNameRequest {

    @NotBlank
    @Schema(example = "New Name", description = "New name for the franchise, branch, and product")
    private String newName;

}
