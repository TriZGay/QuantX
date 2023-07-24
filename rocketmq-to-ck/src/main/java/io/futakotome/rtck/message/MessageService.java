package io.futakotome.rtck.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.HashMap;
import java.util.Map;

@Service
public class MessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);
    private final ObjectMapper objectMapper;
    private static final Map<String, Sinks.Many<String>> sinks = new HashMap<>();

    public MessageService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void onNext(Message text, String session) {
        if (!sinks.containsKey(session)) return;
        try {
            String payload = objectMapper.writeValueAsString(text);
            sinks.get(session).emitNext(payload, Sinks.EmitFailureHandler.FAIL_FAST);
        } catch (JsonProcessingException e) {
            LOGGER.error("发送消息 {} 到 session {} 失败", text, session, e);
        }
    }

    public Flux<String> getMessages(String session) {
        sinks.putIfAbsent(session, Sinks.many().multicast().onBackpressureBuffer());
        return sinks.get(session).asFlux();
    }
}
