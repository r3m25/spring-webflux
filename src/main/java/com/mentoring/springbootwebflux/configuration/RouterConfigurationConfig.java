package com.mentoring.springbootwebflux.configuration;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.mentoring.springbootwebflux.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfigurationConfig {

    private final static String URL_PATTERN = "api/v2/products/";

    @Bean
    public RouterFunction<ServerResponse> routes(ProductHandler productHandler) {
        return
                route(
                        GET(URL_PATTERN), productHandler::productList)
                .andRoute(
                        GET(URL_PATTERN + "{id}"), productHandler::productDetail)
                .andRoute(
                        POST(URL_PATTERN), productHandler::productSave
                ).andRoute(
                        PUT(URL_PATTERN + "{id}"), productHandler::productUpdate
                ).andRoute(
                        DELETE(URL_PATTERN + "{id}"), productHandler::productDeleted
                );
    }
}