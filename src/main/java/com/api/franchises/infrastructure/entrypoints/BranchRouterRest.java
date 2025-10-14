package com.api.franchises.infrastructure.entrypoints;

import com.api.franchises.infrastructure.entrypoints.handler.BranchHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.api.franchises.infrastructure.entrypoints.util.Constants.BASE_URL;

@Configuration
public class BranchRouterRest {

    @Bean
    public RouterFunction<ServerResponse> branchRouterFunction(BranchHandlerImpl handler) {
        return RouterFunctions.route()
                .POST(BASE_URL + "/franchises/{franchiseId}/branches", handler::createBranch)
                .PATCH(BASE_URL + "/franchises/{franchiseId}/branches/{branchId}/name", handler::updateNameBranch )
                .build();
    }

}
