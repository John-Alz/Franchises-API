package com.api.franchises.infrastructure.entrypoints.util;

import com.api.franchises.domain.enums.TechnicalMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;

public class ResponseHandler {

    private ResponseHandler () {}

    public static Mono<ServerResponse> buildErrorResponse(HttpStatus status, String identifier, TechnicalMessage message, List<ErrorDTO> errors) {
        return Mono.defer(() -> {
            APIResponse apiResponse = APIResponse
                    .builder()
                    .code(message.getCode())
                    .message(message.getMessage())
                    .identifier(identifier)
                    .date(Instant.now().toString())
                    .errors(errors)
                    .build();
            return ServerResponse.status(status)
                    .bodyValue(apiResponse);
        });
    }

    public static  <T> Mono<ServerResponse> buildSuccessResponse(HttpStatus status, String identifier, TechnicalMessage message, T data) {
        return Mono.defer(() -> {
            APIResponse apiResponse = APIResponse.builder()
                    .code(message.getCode())
                    .message(message.getMessage())
                    .identifier(identifier)
                    .date(Instant.now().toString())
                    .data(data)
                    .build();
            return ServerResponse.status(status).bodyValue(apiResponse);
        });
    }


}
