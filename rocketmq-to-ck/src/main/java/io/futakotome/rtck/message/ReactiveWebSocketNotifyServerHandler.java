package io.futakotome.rtck.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.CloseStatus;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class ReactiveWebSocketNotifyServerHandler
        extends AbstractWebSocketServerHandler
        implements WebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveWebSocketNotifyServerHandler.class);

    protected ReactiveWebSocketNotifyServerHandler(ObjectMapper objectMapper, ReactiveWebSocketListener listener, MessageService messageService) {
        super(objectMapper, listener, messageService);
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        String path = session.getHandshakeInfo().getUri().getPath();
        Flux<WebSocketMessage> messages = getMessageService()
                .getMessages(ReactiveWebSocketHandlerMapping.NOTIFY_PATH)
                .map(session::textMessage);

        Flux<WebSocketMessage> reading = session.receive()
                .doOnNext(webSocketMessage ->
                        onMessage(webSocketMessage.getPayloadAsText(), ReactiveWebSocketHandlerMapping.NOTIFY_PATH));
//        return session.send(Mono.delay(Duration.ofMillis(100)).thenMany(Mono.just(session.textMessage("dddd"))));
        Mono<CloseStatus> printCloseStatus = session.closeStatus()
                .doOnNext(closeStatus ->
                        LOGGER.info("{},连接状态:{},{}", path, closeStatus.getCode(),
                                closeStatus.getReason()));
//
        return session.send(messages).and(reading)
                .and(printCloseStatus);
    }

}
