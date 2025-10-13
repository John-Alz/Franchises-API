package com.api.franchises.infrastructure.entrypoints;

import com.api.franchises.infrastructure.entrypoints.handler.BranchHandlerImpl;
import com.api.franchises.infrastructure.entrypoints.handler.FranchiseHandlerImpl;
import com.api.franchises.infrastructure.entrypoints.handler.ProductHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.api.franchises.infrastructure.entrypoints.util.Constants.BASE;

@Configuration
public class ProductRouterRest {

    @Bean
    public RouterFunction<ServerResponse> productRouterFunction(ProductHandlerImpl handler) {
        return RouterFunctions.route()
                .POST(BASE + "/branches/{branchId}/products", handler::createProduct)
                .DELETE(BASE + "/branches/{branchId}/products/{productId}", handler::deleteProduct)
                .PATCH(BASE + "/branches/{branchId}/products/{productId}", handler::updateStockProduct)
                .PATCH(BASE + "/branches/{branchId}/products/{productId}/name", handler::updateNameProduct)
                .build();
    }

}
