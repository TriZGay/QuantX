package io.futakotome.stock.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration(proxyBeanMethods = false)
public class RoutingConfiguration {
    private static final RequestPredicate ACCEPT_JSON = accept(MediaType.APPLICATION_JSON);
    private static final String PREFIX = "/stock";

    @Bean
    public RouterFunction<ServerResponse> monoRouterFunction(StockRouterHandler handler) {
        return route()
                .POST(PREFIX + "/", ACCEPT_JSON, handler::stocks)
                .GET(PREFIX + "/{stockId}", ACCEPT_JSON, handler::getById)
                .DELETE(PREFIX + "/{stockId}", ACCEPT_JSON, handler::deleteById)
                .PUT(PREFIX + "/{stockId}", ACCEPT_JSON, handler::updateById)
                .build();

    }
}
