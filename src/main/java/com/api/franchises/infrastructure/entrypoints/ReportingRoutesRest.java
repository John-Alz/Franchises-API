package com.api.franchises.infrastructure.entrypoints;

import com.api.franchises.infrastructure.entrypoints.handler.ProductHandlerImpl;
import com.api.franchises.infrastructure.entrypoints.handler.ReportingHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.api.franchises.infrastructure.entrypoints.util.Constants.BASE;

@Configuration
public class ReportingRoutesRest {

    @Bean
    public RouterFunction<ServerResponse> repoprtingRouterFunction(ReportingHandlerImpl handler) {
        return RouterFunctions.route()
                .GET(BASE + "/franchises/{franchiseId}/top-products-by-branch", handler::topProductPerBranch)
                .build();
    }

}
