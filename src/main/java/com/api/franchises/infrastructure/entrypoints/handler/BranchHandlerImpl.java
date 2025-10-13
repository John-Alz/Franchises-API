package com.api.franchises.infrastructure.entrypoints.handler;

import com.api.franchises.domain.api.BranchServicePort;
import com.api.franchises.domain.enums.TechnicalMessage;
import com.api.franchises.domain.exceptions.BusinessException;
import com.api.franchises.domain.exceptions.TechnicalException;
import com.api.franchises.infrastructure.entrypoints.dto.BranchDTO;
import com.api.franchises.infrastructure.entrypoints.mapper.BranchMapper;
import com.api.franchises.infrastructure.entrypoints.util.ErrorDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.List;
import java.util.UUID;

import static com.api.franchises.infrastructure.entrypoints.util.Constants.FRANCHISE_ERROR;
import static com.api.franchises.infrastructure.entrypoints.util.Constants.X_MESSAGE_ID;
import static com.api.franchises.infrastructure.entrypoints.util.ResponseHandler.buildErrorResponse;
import static com.api.franchises.infrastructure.entrypoints.util.ResponseHandler.buildSuccessResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class BranchHandlerImpl {

    private final BranchServicePort branchServicePort;
    private final BranchMapper branchMapper;

    public Mono<ServerResponse> createBranch(ServerRequest request) {
        final String messageId = getMessageId(request) != null
                ? getMessageId(request)
                : UUID.randomUUID().toString();
        Long franchiseId = Long.valueOf(request.pathVariable("franchiseId"));
        return request.bodyToMono(BranchDTO.class)
                .flatMap(branchDTO -> branchServicePort.saveBranch(franchiseId, branchMapper.branchDTOToBranch(branchDTO), messageId)
                        .doOnSuccess(savedBranch -> log.info("Branch created successfully with messageId: {}", messageId))
                )
                .flatMap(savedBranch -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .bodyValue(TechnicalMessage.BRANCH_CREATED.getMessage()))
                .contextWrite(Context.of(X_MESSAGE_ID, messageId))
                .doOnError(ex -> log.error(FRANCHISE_ERROR, ex))
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


    private String getMessageId(ServerRequest serverRequest) {
        return serverRequest.headers().firstHeader(X_MESSAGE_ID);
    }

}
