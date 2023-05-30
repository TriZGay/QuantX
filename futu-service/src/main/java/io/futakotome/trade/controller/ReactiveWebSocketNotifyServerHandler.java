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
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class ReactiveWebSocketNotifyServerHandler implements WebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveWebSocketNotifyServerHandler.class);
    private final ObjectMapper objectMapper;
    private final ReactiveWebSocketListener listener;
    private final MessageService messageService;

    public ReactiveWebSocketNotifyServerHandler(ObjectMapper objectMapper, ReactiveWebSocketListener listener, MessageService messageService) {
        this.objectMapper = objectMapper;
        this.listener = listener;
        this.messageService = messageService;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        String sessionId = getSessionId(session.getHandshakeInfo().getUri());

        Flux<WebSocketMessage> messages = messageService.getMessages(sessionId)
                .map(session::textMessage);

        Flux<WebSocketMessage> reading = session.receive()
                .doOnNext(webSocketMessage ->
                        onMessage(webSocketMessage.getPayloadAsText(), sessionId));

        return session.send(messages).and(reading);
    }

    private void onMessage(String payload, String sessionId) {
        try {
            Message message = objectMapper.readValue(payload, Message.class);
            listener.onMessage(message, sessionId);
        } catch (JsonProcessingException e) {
            LOGGER.error("处理消息出错.", e);
        }
    }

    private String getSessionId(URI wsUri) {
        return wsUri.getQuery().split("=")[1];
    }
}
