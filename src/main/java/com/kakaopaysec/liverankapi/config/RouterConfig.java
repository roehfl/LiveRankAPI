package com.kakaopaysec.liverankapi.config;

import com.kakaopaysec.liverankapi.handler.APIHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

//    @Bean
//    public RouterFunction<ServerResponse> routeFunction(APIHandler handler) {
//        return route(GET("/api/hello"), handler::hello)
//                .andRoute(GET("/api/goodbye"), handler::goodbye);
//    }
}