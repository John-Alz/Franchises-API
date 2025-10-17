package com.api.franchises.infrastructure.entrypoints.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@Schema(name = "TopProductPerBranchResponse", description = "Top product per branch")
public class TopProductPerBranchResponse {

    @Schema(description = "Branch ID", example = "10")
    private Long branchId;
    @Schema(description = "Branch name", example = "Downtown branch")
    private String branchName;

    @Schema(description = "Product that ranks first in the branch")
    private ProductSummary product;

}
