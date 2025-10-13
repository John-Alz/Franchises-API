package com.api.franchises.infrastructure.entrypoints.handler;

import com.api.franchises.domain.api.ProductServicePort;
import com.api.franchises.domain.enums.TechnicalMessage;
import com.api.franchises.domain.exceptions.BusinessException;
import com.api.franchises.domain.exceptions.TechnicalException;
import com.api.franchises.infrastructure.entrypoints.dto.ProductDTO;
import com.api.franchises.infrastructure.entrypoints.dto.UpdateNameRequest;
import com.api.franchises.infrastructure.entrypoints.dto.UpdateStockRequest;
import com.api.franchises.infrastructure.entrypoints.mapper.ProductMapper;
import com.api.franchises.infrastructure.entrypoints.util.ErrorDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import static com.api.franchises.infrastructure.entrypoints.util.Constants.*;
import static com.api.franchises.infrastructure.entrypoints.util.MessageId.getMessageId;
import static com.api.franchises.infrastructure.entrypoints.util.ResponseHandler.buildErrorResponse;


import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductHandlerImpl {

    private final ProductServicePort productServicePort;
    private final ProductMapper productMapper;

    public Mono<ServerResponse> createProduct(ServerRequest request) {
        final String messageId = getMessageId(request) != null
                ? getMessageId(request)
                : UUID.randomUUID().toString();
        Long branchId = Long.valueOf(request.pathVariable("branchId"));
        return request.bodyToMono(ProductDTO.class)
                .flatMap(product -> productServicePort.saveProduct(branchId, productMapper.productDTOToProduct(product), messageId)
                        .doOnSuccess(savedUser -> log.info("Product created successfully with messageId: {}", messageId))
                )
                .flatMap(savedProduct -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .bodyValue(TechnicalMessage.PRODUCT_CREATED.getMessage()))
                .contextWrite(Context.of(X_MESSAGE_ID, messageId))
                .doOnError(ex -> log.error(PRODUCT_ERROR, ex))
                .onErrorResume(BusinessException.class, ex -> buildErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        messageId,
                        TechnicalMessage.INVALID_PARAMETERS,
                        List.of(ErrorDTO.builder()
                                .code(ex.getTechnicalMessage().getCode())
                                .message(ex.getTechnicalMessage().getMessage())
                                .param(ex.getTechnicalMessage().getParam())
                                .build())
                ))
                .onErrorResume(TechnicalException.class, ex -> buildErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        messageId,
                        TechnicalMessage.INTERNAL_ERROR,
                        List.of(ErrorDTO.builder()
                                .code(ex.getTechnicalMessage().getCode())
                                .message(ex.getTechnicalMessage().getMessage())
                                .param(ex.getTechnicalMessage().getParam())
                                .build())));
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        final String messageId = getMessageId(request) != null
                ? getMessageId(request)
                : UUID.randomUUID().toString();
        Long branchId = Long.valueOf(request.pathVariable("branchId"));
        Long productId = Long.valueOf(request.pathVariable("productId"));
        return productServicePort.deleteProduct(branchId, productId, messageId)
                .then(ServerResponse.noContent().build())
                .contextWrite(Context.of(X_MESSAGE_ID, messageId))
                .doOnError(ex -> log.error(PRODUCT_ERROR, ex))
                .onErrorResume(BusinessException.class, ex -> buildErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        messageId,
                        TechnicalMessage.INVALID_PARAMETERS,
                        List.of(ErrorDTO.builder()
                                .code(ex.getTechnicalMessage().getCode())
                                .message(ex.getTechnicalMessage().getMessage())
                                .param(ex.getTechnicalMessage().getParam())
                                .build())
                ))
                .onErrorResume(TechnicalException.class, ex -> buildErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        messageId,
                        TechnicalMessage.INTERNAL_ERROR,
                        List.of(ErrorDTO.builder()
                                .code(ex.getTechnicalMessage().getCode())
                                .message(ex.getTechnicalMessage().getMessage())
                                .param(ex.getTechnicalMessage().getParam())
                                .build())));
    }

    public Mono<ServerResponse> updateStockProduct(ServerRequest request) {
        final String messageId = getMessageId(request) != null
                ? getMessageId(request)
                : UUID.randomUUID().toString();
        Long branchId = Long.valueOf(request.pathVariable("branchId"));
        Long productId = Long.valueOf(request.pathVariable("productId"));
        return request.bodyToMono(UpdateStockRequest.class)
                .flatMap(dto -> productServicePort.updateStockProduct(branchId, productId, dto.getStock()))
                .then(ServerResponse.noContent().build())
                .contextWrite(Context.of(X_MESSAGE_ID, messageId))
                .doOnError(ex -> log.error(PRODUCT_ERROR, ex))
                .onErrorResume(BusinessException.class, ex -> buildErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        messageId,
                        TechnicalMessage.INVALID_PARAMETERS,
                        List.of(ErrorDTO.builder()
                                .code(ex.getTechnicalMessage().getCode())
                                .message(ex.getTechnicalMessage().getMessage())
                                .param(ex.getTechnicalMessage().getParam())
                                .build())
                ))
                .onErrorResume(TechnicalException.class, ex -> buildErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        messageId,
                        TechnicalMessage.INTERNAL_ERROR,
                        List.of(ErrorDTO.builder()
                                .code(ex.getTechnicalMessage().getCode())
                                .message(ex.getTechnicalMessage().getMessage())
                                .param(ex.getTechnicalMessage().getParam())
                                .build())));
    }


    public Mono<ServerResponse> updateNameProduct(ServerRequest request) {
        final String messageId = getMessageId(request) != null
                ? getMessageId(request)
                : UUID.randomUUID().toString();
        Long branchId = Long.valueOf(request.pathVariable("branchId"));
        Long productId = Long.valueOf(request.pathVariable("productId"));
        return request.bodyToMono(UpdateNameRequest.class)
                .flatMap(dto -> productServicePort.updateNameProduct(branchId, productId, dto.getNewName()))
                .then(ServerResponse.noContent().build())
                .contextWrite(Context.of(X_MESSAGE_ID, messageId))
                .doOnError(ex -> log.error(PRODUCT_ERROR, ex))
                .onErrorResume(BusinessException.class, ex -> buildErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        messageId,
                        TechnicalMessage.INVALID_PARAMETERS,
                        List.of(ErrorDTO.builder()
                                .code(ex.getTechnicalMessage().getCode())
                                .message(ex.getTechnicalMessage().getMessage())
                                .param(ex.getTechnicalMessage().getParam())
                                .build())
                ))
                .onErrorResume(TechnicalException.class, ex -> buildErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        messageId,
                        TechnicalMessage.INTERNAL_ERROR,
                        List.of(ErrorDTO.builder()
                                .code(ex.getTechnicalMessage().getCode())
                                .message(ex.getTechnicalMessage().getMessage())
                                .param(ex.getTechnicalMessage().getParam())
                                .build())));
    }



}
