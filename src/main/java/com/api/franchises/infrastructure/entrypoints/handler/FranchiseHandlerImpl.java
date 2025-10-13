package com.api.franchises.infrastructure.entrypoints.handler;

import com.api.franchises.domain.api.FranchiseServicePort;
import com.api.franchises.domain.enums.TechnicalMessage;
import com.api.franchises.domain.exceptions.BusinessException;
import com.api.franchises.domain.exceptions.TechnicalException;
import com.api.franchises.infrastructure.entrypoints.dto.FranchiseDTO;
import com.api.franchises.infrastructure.entrypoints.mapper.FranchiseMapper;
import com.api.franchises.infrastructure.entrypoints.util.APIResponse;
import com.api.franchises.infrastructure.entrypoints.util.ErrorDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.api.franchises.infrastructure.entrypoints.util.Constants.FRANCHISE_ERROR;
import static com.api.franchises.infrastructure.entrypoints.util.Constants.X_MESSAGE_ID;
import static com.api.franchises.infrastructure.entrypoints.util.ResponseHandler.buildSuccessResponse;
import static com.api.franchises.infrastructure.entrypoints.util.ResponseHandler.buildErrorResponse;




@Slf4j
@Component
@RequiredArgsConstructor
public class FranchiseHandlerImpl {

    private final FranchiseServicePort franchiseServicePort;
    private final FranchiseMapper franchiseMapper;

    public Mono<ServerResponse> createFranchise(ServerRequest request) {
        final String messageId = getMessageId(request) != null
                ? getMessageId(request)
                : UUID.randomUUID().toString();
        return request.bodyToMono(FranchiseDTO.class)
                .flatMap(franchise -> franchiseServicePort.saveFranchise(franchiseMapper.franchiseDTOToFranchise(franchise), messageId)
                        .doOnSuccess(savedFranchise -> log.info("Franchise created successfully with messageId: {}", messageId))
                )
                .flatMap(franchise -> buildSuccessResponse(
                        HttpStatus.CREATED,
                        messageId,
                        TechnicalMessage.FRANCHISE_CREATED,
                        franchise
                ))
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
