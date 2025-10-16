package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.dto;

public record TopProductPerBranchRow(
        Long branchid,
        String branchname,
        Long productid,
        String productname,
        Integer stock
) {

}
