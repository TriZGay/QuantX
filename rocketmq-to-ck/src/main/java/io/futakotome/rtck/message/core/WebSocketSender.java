package io.futakotome.rtck.message.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.futakotome.rtck.message.dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.FluxSink;

public class WebSocketSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketSender.class);
    private final WebSocketSession session;
    private final FluxSink<WebSocketMessage> sink;
    private final ObjectMapper objectMapper;

    public WebSocketSender(WebSocketSession session, FluxSink<WebSocketMessage> sink, ObjectMapper objectMapper) {
        this.session = session;
        this.sink = sink;
        this.objectMapper = objectMapper;
    }

    public void sendData(Message message) {
        try {
            String messagePayload = objectMapper.writeValueAsString(message);
            sink.next(session.textMessage(messagePayload));
        } catch (JsonProcessingException e) {
            LOGGER.error("发送消息失败.", e);
        }
    }
}
