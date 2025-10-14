package com.api.franchises.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TechnicalMessage {

    INTERNAL_ERROR("500","Something went wrong, please try again", ""),
    INTERNAL_ERROR_IN_ADAPTERS("PRC501","Something went wrong in adapters, please try again", ""),
    INVALID_REQUEST("400", "Bad Request, please verify data", ""),
    INVALID_PARAMETERS(INVALID_REQUEST.getCode(), "Bad Parameters, please verify data", ""),
    INVALID_MESSAGE_ID("404", "Invalid Message ID, please verify", "messageId"),
    UNSUPPORTED_OPERATION("501", "Method not supported, please try again", ""),
    FRANCHISE_CREATED("201", "Franchise created successfully", ""),
    FRANCHISE_ALREADY_EXISTS("400", "The franchise is already registered.", ""),
    FRANCHISE_NOT_FOUND("400", "The franchise does not exist.", ""),
    BRANCH_NOT_IN_FRANCHISE_OR_NOT_FOUND("400", "The branch does not belong to this franchise or does not exist.", ""),
    BRANCH_CREATED("201", "Branch created successfully", ""),
    BRANCH_NOT_FOUND("400", "The branch does not exist.", ""),
    PRODUCT_CREATED("201", "Product created successfully", ""),
    PRODUCT_DELETED("200", "Product deleted successfully", ""),
    PRODUCT_NOT_IN_BRANCH_OR_NOT_FOUND("400", "The product does not belong to this branch or does not exist.", ""),
    NEGATIVE_STOCK("400", "The product's stock cannot be less than zero.", "");

    private final String code;
    private final String message;
    private final String param;

}