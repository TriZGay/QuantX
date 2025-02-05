package io.futakotome.trade.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.futakotome.rtck.annotation.WebSocketMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.CloseStatus;
import org.springframework.web.reactive.socket.HandshakeInfo;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@WebSocketMapping("/websocket/notify")
public class ReactiveWebSocketNotifyServerHandler
        extends AbstractWebSocketServerHandler implements WebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveWebSocketNotifyServerHandler.class);
    private final ObjectMapper objectMapper;
    private final ConcurrentHashMap<String, WebSocketSender> senderMap;

    public ReactiveWebSocketNotifyServerHandler(ConcurrentHashMap<String, WebSocketSender> sendMap, ObjectMapper objectMapper) {
        this.senderMap = sendMap;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        HandshakeInfo handshakeInfo = session.getHandshakeInfo();
        Map<String, String> queryMap = getQueryMap(handshakeInfo.getUri().getQuery());
        //todo 定点推送?
        String id = queryMap.getOrDefault("id", NOTIFY_TAG);
        Mono<CloseStatus> close = session.closeStatus()
                .doOnNext(closeStatus -> {
                    LOGGER.info("连接关闭,{}:{}", closeStatus.getCode(),
                            closeStatus.getReason());
                    senderMap.remove(id);
                });
        Mono<Void> input = session.receive()
                .doOnNext(webSocketMessage -> {
                    String message = webSocketMessage.getPayloadAsText();
                    LOGGER.info(id + ": " + message);
                }).then();
        Mono<Void> output = session
                .send(Flux.create(sink ->
                        senderMap.put(id, new WebSocketSender(session, sink, objectMapper))));
        /*
          Mono.zip() 会将多个 Mono 合并为一个新的 Mono，任何一个 Mono 产生 error 或 complete 都会导致合并后的 Mono
          也随之产生 error 或 complete，此时其它的 Mono 则会被执行取消操作。
         */
        return Mono.zip(input, output, close).then();
    }

}
