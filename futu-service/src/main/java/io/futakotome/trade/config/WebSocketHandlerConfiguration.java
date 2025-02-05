package io.futakotome.trade.config;

import io.futakotome.trade.annotation.WebSocketMappingAnnotationHandler;
import io.futakotome.trade.ws.WebSocketSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class WebSocketHandlerConfiguration {
    @Bean
    public HandlerMapping webSocketHandlerMapping() {
        return new WebSocketMappingAnnotationHandler();
    }

    @Bean
    public ConcurrentHashMap<String, WebSocketSender> senderMap() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
