package com.api.franchises.infrastructure.entrypoints;

import com.api.franchises.infrastructure.entrypoints.dto.BranchDTO;
import com.api.franchises.infrastructure.entrypoints.dto.UpdateNameRequest;
import com.api.franchises.infrastructure.entrypoints.handler.BranchHandlerImpl;
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
public class BranchRouterRest {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = BASE_URL + "/franchises/{franchiseId}/branches",
                    method = RequestMethod.POST,
                    produces = { MediaType.TEXT_PLAIN_VALUE },
                    consumes = { MediaType.APPLICATION_JSON_VALUE },
                    beanClass = BranchHandlerImpl.class,
                    beanMethod = "createBranch",
                    operation = @Operation(
                            operationId = "createBranch",
                            summary = "Create a branch",
                            tags = {"Branches"},
                            description = "Creates a new branch within the specified franchise.",
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
                                            schema = @Schema(implementation = BranchDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Branch created",
                                            content = @Content(
                                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                                    schema = @Schema(type = "string", example = "Branch created successfully")
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
                    path = BASE_URL + "/franchises/{franchiseId}/branches/{branchId}/name",
                    method = RequestMethod.PATCH,
                    produces = { MediaType.APPLICATION_JSON_VALUE },
                    consumes = { MediaType.APPLICATION_JSON_VALUE },
                    beanClass = BranchHandlerImpl.class,
                    beanMethod = "updateNameBranch",
                    operation = @Operation(
                            operationId = "updateNameBranch",
                            summary = "Update a branch name",
                            tags = {"Branches"},
                            description = "Updates the name of the branch indicated within the given franchise.",
                            parameters = {
                                    @Parameter(
                                            name = "franchiseId",
                                            in = ParameterIn.PATH,
                                            required = true,
                                            description = "Franchise ID",
                                            schema = @Schema(type = "integer", format = "int64", example = "1")
                                    ),
                                    @Parameter(
                                            name = "branchId",
                                            in = ParameterIn.PATH,
                                            required = true,
                                            description = "Branch ID",
                                            schema = @Schema(type = "integer", format = "int64", example = "10")
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
    public RouterFunction<ServerResponse> branchRouterFunction(BranchHandlerImpl handler) {
        return RouterFunctions.route()
                .POST(BASE_URL + "/franchises/{franchiseId}/branches", handler::createBranch)
                .PATCH(BASE_URL + "/franchises/{franchiseId}/branches/{branchId}/name", handler::updateNameBranch )
                .build();
    }

}
