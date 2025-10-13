package com.api.franchises.infrastructure.entrypoints;

import com.api.franchises.infrastructure.entrypoints.handler.BranchHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.api.franchises.infrastructure.entrypoints.util.Constants.BASE;

@Configuration
public class BranchRouterRest {

    @Bean
    public RouterFunction<ServerResponse> BranchrouterFunction(BranchHandlerImpl handler) {
        return RouterFunctions.route()
                .POST(BASE + "/franchises/{franchiseId}/branches", handler::createBranch)
                .build();
    }

}
