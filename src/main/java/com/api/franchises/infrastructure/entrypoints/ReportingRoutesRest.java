package com.api.franchises.infrastructure.entrypoints;

import com.api.franchises.infrastructure.entrypoints.dto.TopProductPerBranchResponse;
import com.api.franchises.infrastructure.entrypoints.handler.ReportingHandlerImpl;
import com.api.franchises.infrastructure.entrypoints.util.ErrorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.api.franchises.infrastructure.entrypoints.util.Constants.BASE_URL;

@Configuration
public class ReportingRoutesRest {

    @Bean
    @RouterOperation(
            path = BASE_URL + "/franchises/{franchiseId}/top-products-by-branch",
            method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE },
            beanClass = ReportingHandlerImpl.class,
            beanMethod = "topProductPerBranch",
            operation = @Operation(
                    operationId = "topProductPerBranch",
                    summary = "Top products by branch",
                    tags = {"Report"},
                    description = "Returns, for each branch of the franchise, the best-performing product (according to your business rule).",
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
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "List of top products per branch",
                                    content = @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = TopProductPerBranchResponse.class))
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
    )
    public RouterFunction<ServerResponse> repoprtingRouterFunction(ReportingHandlerImpl handler) {
        return RouterFunctions.route()
                .GET(BASE_URL + "/franchises/{franchiseId}/top-products-by-branch", handler::topProductPerBranch)
                .build();
    }

}
