package com.api.franchises.infrastructure.entrypoints;

import com.api.franchises.infrastructure.entrypoints.dto.FranchiseDTO;
import com.api.franchises.infrastructure.entrypoints.dto.UpdateNameRequest;
import com.api.franchises.infrastructure.entrypoints.handler.FranchiseHandlerImpl;
import com.api.franchises.infrastructure.entrypoints.util.ErrorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static com.api.franchises.infrastructure.entrypoints.util.Constants.BASE_URL;

@Configuration
public class FranchiseRouterRest {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = BASE_URL + "/franchises",
                    produces = { MediaType.APPLICATION_JSON_VALUE },
                    consumes = { MediaType.APPLICATION_JSON_VALUE },
                    method = RequestMethod.POST,
                    beanClass = FranchiseHandlerImpl.class,
                    beanMethod = "createFranchise",
                    operation = @Operation(
                            operationId = "createFranchise",
                            summary = "Create a franchise",
                            tags = {"Franchises"},
                            description = "Creates a new franchise and returns the created resource.",
                            parameters = {
                                    @Parameter(
                                            name = "X-Message-Id",
                                            in = ParameterIn.HEADER,
                                            required = false,
                                            description = "Optional correlation ID"
                                    )
                            },
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = FranchiseDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Franchise created",
                                            content = @Content(
                                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                                    schema = @Schema(type = "string", example = "Franchise created successfully.")
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Invalid parameters",
                                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDTO.class)))
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal error",
                                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDTO.class)))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = BASE_URL + "/franchises/{franchiseId}/name",
                    produces = { MediaType.APPLICATION_JSON_VALUE },
                    consumes = { MediaType.APPLICATION_JSON_VALUE },
                    method = RequestMethod.PATCH,
                    beanClass = FranchiseHandlerImpl.class,
                    beanMethod = "updateNameFranchise",
                    operation = @Operation(
                            operationId = "updateNameFranchise",
                            summary = "Update a franchise name",
                            tags = {"Franchises"},
                            description = "Updates the name of the franchise specified by its ID.",
                            parameters = {
                                    @Parameter(
                                            name = "franchiseId",
                                            in = ParameterIn.PATH,
                                            required = true,
                                            description = "Franchise ID",
                                            schema = @Schema(type = "integer", format = "int64", example = "1")
                                    ),
                                    @Parameter(
                                            name = "X-Message-Id",
                                            in = ParameterIn.HEADER,
                                            required = false,
                                            description = "Optional correlation ID"
                                    )
                            },
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = UpdateNameRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "204",
                                            description = "Name updated (no content)"
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Invalid parameters",
                                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDTO.class)))
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal error",
                                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorDTO.class)))
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> franchiseRouterFunction(FranchiseHandlerImpl handler) {
        return RouterFunctions.route()
                .POST(BASE_URL + "/franchises", handler::createFranchise)
                .PATCH(BASE_URL + "/franchises/{franchiseId}/name", handler::updateNameFranchise)
                .build();
    }

}
