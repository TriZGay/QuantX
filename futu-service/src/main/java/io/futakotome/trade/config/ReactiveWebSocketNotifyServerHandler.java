package io.futakotome.trade.config;

import io.futakotome.trade.service.FTQotService;
import io.futakotome.trade.utils.ListenableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Timer;

public class ReactiveWebSocketNotifyServerHandler implements WebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveWebSocketNotifyServerHandler.class);
    private final FTQotService ftQotService;
    public static final String NOTIFY_KEY = "notify";
    public static final ListenableMap<String, String> NOTIFY_MAP = new ListenableMap
            .Builder<String, String>()
            .map(new HashMap<>())
            .onModified(map -> LOGGER.info("执行修改 操作:" + map.values())).build();

    public ReactiveWebSocketNotifyServerHandler(FTQotService ftQotService) {
        this.ftQotService = ftQotService;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .doOnNext(message -> {
                    LOGGER.info("会话属性:{}", session.getAttributes());
                    LOGGER.info("接收消息:{}", message);
                }).zipWith(session.send(Flux.create(webSocketMessageFluxSink ->{
                    webSocketMessageFluxSink.next()
                })))
                .then();
    }
}
