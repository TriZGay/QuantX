package io.futakotome.stock.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class StockRouterHandler {
    public Mono<ServerResponse> stocks(ServerRequest request) {
        return Mono.create(serverResponseMonoSink -> {

        });
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        return Mono.create(serverResponseMonoSink -> {

        });
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        return Mono.create(serverResponseMonoSink -> {

        });
    }

    public Mono<ServerResponse> updateById(ServerRequest request) {
        return Mono.create(serverResponseMonoSink -> {

        });
    }
}
