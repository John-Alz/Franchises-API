package com.api.franchises.infrastructure.entrypoints;

import com.api.franchises.infrastructure.entrypoints.handler.FranchiseHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class FranchiseRouterRest {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(FranchiseHandlerImpl handler) {
        return RouterFunctions.route()
                .POST("/franchises", handler::createFranchise)
                .build();
    }

}
