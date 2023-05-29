package io.futakotome.trade.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactiveWebSocketServerHandler implements WebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveWebSocketServerHandler.class);

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .doOnNext(message -> LOGGER.info("接收:{}", message))
                .zipWith(session.send(Flux.create(webSocketMessageFluxSink -> {
                    webSocketMessageFluxSink.next(session.textMessage("好!"));
                })))
                .then();
    }
}
