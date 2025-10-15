package com.api.franchises.infrastructure.entrypoints;

import com.api.franchises.infrastructure.entrypoints.dto.ProductDTO;
import com.api.franchises.infrastructure.entrypoints.dto.UpdateNameRequest;
import com.api.franchises.infrastructure.entrypoints.dto.UpdateStockRequest;
import com.api.franchises.infrastructure.entrypoints.handler.ProductHandlerImpl;
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
public class ProductRouterRest {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = BASE_URL + "/branches/{branchId}/products",
                    method = RequestMethod.POST,
                    consumes = { MediaType.APPLICATION_JSON_VALUE },
                    produces = { MediaType.TEXT_PLAIN_VALUE },
                    beanClass = ProductHandlerImpl.class,
                    beanMethod = "createProduct",
                    operation = @Operation(
                            operationId = "createProduct",
                            summary = "Create a product in a branch",
                            tags = {"Products"},
                            description = "Creates a product within the specified branch.",
                            parameters = {
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
                                            schema = @Schema(implementation = ProductDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Product created",
                                            content = @Content(
                                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                                    schema = @Schema(type = "string", example = "Product created successfully")
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
                    path = BASE_URL + "/branches/{branchId}/products/{productId}",
                    method = RequestMethod.DELETE,
                    produces = { MediaType.APPLICATION_JSON_VALUE },
                    beanClass = ProductHandlerImpl.class,
                    beanMethod = "deleteProduct",
                    operation = @Operation(
                            operationId = "deleteProduct",
                            summary = "Delete a product",
                            tags = {"Products"},
                            description = "Deletes the specified product from the given branch.",
                            parameters = {
                                    @Parameter(
                                            name = "branchId",
                                            in = ParameterIn.PATH,
                                            required = true,
                                            description = "Branch ID",
                                            schema = @Schema(type = "integer", format = "int64", example = "10")
                                    ),
                                    @Parameter(
                                            name = "productId",
                                            in = ParameterIn.PATH,
                                            required = true,
                                            description = "Product ID",
                                            schema = @Schema(type = "integer", format = "int64", example = "1001")
                                    ),
                                    @Parameter(
                                            name = "X-Message-Id",
                                            in = ParameterIn.HEADER,
                                            required = false,
                                            description = "Optional correlation ID"
                                    )
                            },
                            responses = {
                                    @ApiResponse(responseCode = "204", description = "Product deleted"),
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
                    path = BASE_URL + "/branches/{branchId}/products/{productId}",
                    method = RequestMethod.PATCH,
                    consumes = { MediaType.APPLICATION_JSON_VALUE },
                    produces = { MediaType.APPLICATION_JSON_VALUE },
                    beanClass = ProductHandlerImpl.class,
                    beanMethod = "updateStockProduct",
                    operation = @Operation(
                            operationId = "updateStockProduct",
                            summary = "Update a product's stock",
                            tags = {"Products"},
                            description = "Updates the stock of the specified product in the given branch.",
                            parameters = {
                                    @Parameter(
                                            name = "branchId",
                                            in = ParameterIn.PATH,
                                            required = true,
                                            description = "Branch ID",
                                            schema = @Schema(type = "integer", format = "int64", example = "10")
                                    ),
                                    @Parameter(
                                            name = "productId",
                                            in = ParameterIn.PATH,
                                            required = true,
                                            description = "Product ID",
                                            schema = @Schema(type = "integer", format = "int64", example = "1001")
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
                                            schema = @Schema(implementation = UpdateStockRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "204", description = "Stock updated"),
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
                    path = BASE_URL + "/branches/{branchId}/products/{productId}/name",
                    method = RequestMethod.PATCH,
                    consumes = { MediaType.APPLICATION_JSON_VALUE },
                    produces = { MediaType.APPLICATION_JSON_VALUE },
                    beanClass = ProductHandlerImpl.class,
                    beanMethod = "updateNameProduct",
                    operation = @Operation(
                            operationId = "updateNameProduct",
                            summary = "Update a product name",
                            tags = {"Products"},
                            description = "Updates the name of the specified product in the given branch.",
                            parameters = {
                                    @Parameter(
                                            name = "branchId",
                                            in = ParameterIn.PATH,
                                            required = true,
                                            description = "Branch ID",
                                            schema = @Schema(type = "integer", format = "int64", example = "10")
                                    ),
                                    @Parameter(
                                            name = "productId",
                                            in = ParameterIn.PATH,
                                            required = true,
                                            description = "Product ID",
                                            schema = @Schema(type = "integer", format = "int64", example = "1001")
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
                                    @ApiResponse(responseCode = "204", description = "Name updated"),
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
    public RouterFunction<ServerResponse> productRouterFunction(ProductHandlerImpl handler) {
        return RouterFunctions.route()
                .POST(BASE_URL + "/branches/{branchId}/products", handler::createProduct)
                .DELETE(BASE_URL + "/branches/{branchId}/products/{productId}", handler::deleteProduct)
                .PATCH(BASE_URL + "/branches/{branchId}/products/{productId}", handler::updateStockProduct)
                .PATCH(BASE_URL + "/branches/{branchId}/products/{productId}/name", handler::updateNameProduct)
                .build();
    }

}
