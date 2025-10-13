package com.api.franchises.infrastructure.entrypoints.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class TopProductPerBranchResponse {

    private Long branchId;
    private String branchName;
    private ProductSummary product;

}
