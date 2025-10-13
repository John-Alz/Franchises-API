package com.api.franchises.infrastructure.entrypoints.handler;

import com.api.franchises.domain.api.ReportingServicePort;
import com.api.franchises.domain.enums.TechnicalMessage;
import com.api.franchises.domain.exceptions.BusinessException;
import com.api.franchises.domain.exceptions.TechnicalException;
import com.api.franchises.infrastructure.entrypoints.mapper.ReportingMapper;
import com.api.franchises.infrastructure.entrypoints.util.ErrorDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static com.api.franchises.infrastructure.entrypoints.util.MessageId.getMessageId;
import static com.api.franchises.infrastructure.entrypoints.util.ResponseHandler.buildErrorResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportingHandlerImpl {

    private final ReportingServicePort reportingServicePort;
    private final ReportingMapper reportingMapper;

    public Mono<ServerResponse> topProductPerBranch(ServerRequest request) {
        final String messageId = getMessageId(request) != null
                ? getMessageId(request)
                : UUID.randomUUID().toString();
        Long franchiseId = Long.valueOf(request.pathVariable("franchiseId"));
        return reportingServicePort.topProductPerBranch(franchiseId)
                .map(reportingMapper::toResponse)
                .collectList()
                .flatMap(list -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(list))
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
