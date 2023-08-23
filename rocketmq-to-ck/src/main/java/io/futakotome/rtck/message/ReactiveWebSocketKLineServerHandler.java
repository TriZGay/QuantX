//package io.futakotome.rtck.message;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.futakotome.rtck.config.WebSocketHandlerConfiguration;
//import io.futakotome.rtck.message.core.ReactiveWebSocketListener;
//import io.futakotome.rtck.message.core.MessageService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.socket.CloseStatus;
//import org.springframework.web.reactive.socket.WebSocketHandler;
//import org.springframework.web.reactive.socket.WebSocketMessage;
//import org.springframework.web.reactive.socket.WebSocketSession;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@Component
//public class ReactiveWebSocketKLineServerHandler
//        extends AbstractWebSocketServerHandler
//        implements WebSocketHandler {
//    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveWebSocketKLineServerHandler.class);
//
//    protected ReactiveWebSocketKLineServerHandler(ObjectMapper objectMapper, ReactiveWebSocketListener listener, MessageService messageService) {
//        super(objectMapper, listener, messageService);
//    }
//
//    @Override
//    public Mono<Void> handle(WebSocketSession session) {
//        String path = session.getHandshakeInfo().getUri().getPath();
//        Flux<WebSocketMessage> messages = getMessageService()
//                .getMessages(WebSocketHandlerConfiguration.KLINE_PATH)
//                .map(session::textMessage);
//
//        Flux<WebSocketMessage> reading = session.receive()
//                .doOnNext(webSocketMessage ->
//                        onMessage(webSocketMessage.getPayloadAsText(), WebSocketHandlerConfiguration.KLINE_PATH));
//
//        Mono<CloseStatus> printCloseStatus = session.closeStatus()
//                .doOnNext(closeStatus ->
//                        LOGGER.info("{},连接状态:{},{}", path, closeStatus.getCode(),
//                                closeStatus.getReason()));
//
//        return session.send(messages).and(reading)
//                .and(printCloseStatus);
//    }
//
//}
