package io.futakotome.trade.controller;

import io.futakotome.trade.listener.ReactiveWebSocketListener;
import io.futakotome.trade.service.FTQotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactiveWebSocketNotifyServerHandler implements WebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveWebSocketNotifyServerHandler.class);
    private final FTQotService ftQotService;
    private final ReactiveWebSocketListener listener;
//    public static final String NOTIFY_KEY = "notify";
//    public static final ListenableMap<String, String> NOTIFY_MAP = new ListenableMap
//            .Builder<String, String>()
//            .map(new HashMap<>())
//            .onModified(map -> LOGGER.info("执行修改 操作:" + map.values())).build();

    public ReactiveWebSocketNotifyServerHandler(FTQotService ftQotService, ReactiveWebSocketListener listener) {
        this.ftQotService = ftQotService;
        this.listener = listener;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        Flux<WebSocketMessage> messages = ;

        Flux<WebSocketMessage> reading = session.receive()
                .doOnNext(webSocketMessage ->);
//        return session.receive()
//                .map(WebSocketMessage::getPayloadAsText)
//                .doOnNext(message -> {
//                    LOGGER.info("会话属性:{}", session.getAttributes());
//                    LOGGER.info("接收消息:{}", message);
//                }).zipWith(session.send(Flux.create(webSocketMessageFluxSink ->{
//                    webSocketMessageFluxSink.next()
//                })))
//                .then();
    }
}
