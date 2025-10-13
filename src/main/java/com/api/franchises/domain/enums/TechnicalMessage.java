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
    INVALID_EMAIL("403", "Invalid email, please verify", "email"),
    INVALID_MESSAGE_ID("404", "Invalid Message ID, please verify", "messageId"),
    UNSUPPORTED_OPERATION("501", "Method not supported, please try again", ""),
    FRANCHISE_CREATED("201", "Franchise created successfully", ""),
    ADAPTER_RESPONSE_NOT_FOUND("404-0", "invalid email, please verify", ""),
    FRANCHISE_ALREADY_EXISTS("400","La franquicia ya está registrada." ,"" ),
    FRANCHISE_NOT_FOUND("400","La franquicia no existe." ,"" ),
    BRANCH_NOT_IN_FRANCHISE_OR_NOT_FOUND("400", "La sucursal no pertenece a esta franquicia o no existe", ""),
    BRANCH_CREATED("201", "Branch created successfully", ""),
    BRANCH_NOT_FOUND("400","La sucursal no existe." ,"" ),
    PRODUCT_CREATED("201", "Product created successfully", ""),
    PRODUCT_DELETED("200", "Product deleted successfully", ""),
    PRODUCT_NOT_IN_BRANCH_OR_NOT_FOUND("400", "El producto no pertenece a esta sucursal o no existe", ""),
    NEGATIVE_STOCK("400", "El stock del producto no puede ser menor a cero", "");



    private final String code;
    private final String message;
    private final String param;
}