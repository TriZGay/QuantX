package io.futakotome.trade.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.futakotome.trade.listener.Message;
import io.futakotome.trade.listener.ReactiveWebSocketListener;
import io.futakotome.trade.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.CloseStatus;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class ReactiveWebSocketNotifyServerHandler extends AbstractWebSocketServerHandler implements WebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveWebSocketNotifyServerHandler.class);

    protected ReactiveWebSocketNotifyServerHandler(ObjectMapper objectMapper, ReactiveWebSocketListener listener, MessageService messageService) {
        super(objectMapper, listener, messageService);
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        String sessionId = session.getId();
//        getSessionId(session.getHandshakeInfo().getUri());
        String path = session.getHandshakeInfo().getUri().getPath();
        LOGGER.info("Ws path : {}.sessionId : {}", path, sessionId);

        Flux<WebSocketMessage> messages = getMessageService()
                .getMessages(sessionId)
                .map(session::textMessage);

        Flux<WebSocketMessage> reading = session.receive()
                .doOnNext(webSocketMessage ->
                        onMessage(webSocketMessage.getPayloadAsText(), sessionId));

        Mono<CloseStatus> printCloseStatus = session.closeStatus()
                .doOnNext(closeStatus ->
                        LOGGER.info("连接状态:{},{}", closeStatus.getCode(),
                                closeStatus.getReason()));

        return session.send(messages).and(reading)
                .and(printCloseStatus);
    }

}
