package io.futakotome.trade.config;

import io.futakotome.trade.controller.ReactiveWebSocketKLineServerHandler;
import io.futakotome.trade.controller.ReactiveWebSocketNotifyServerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ReactiveWebSocketFTHandlerMapping {
    private final ReactiveWebSocketNotifyServerHandler notifyHandler;
    private final ReactiveWebSocketKLineServerHandler kLineHandler;

    public ReactiveWebSocketFTHandlerMapping(ReactiveWebSocketNotifyServerHandler notifyHandler, ReactiveWebSocketKLineServerHandler kLineHandler) {
        this.notifyHandler = notifyHandler;
        this.kLineHandler = kLineHandler;
    }

    @Bean
    public HandlerMapping webSocketHandlerMapping() {
        Map<String, WebSocketHandler> urlMap = new HashMap<>() {{
            put("/websocket/notify", notifyHandler);
            put("/websocket/kl", kLineHandler);
        }};
        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
        simpleUrlHandlerMapping.setOrder(1);
        simpleUrlHandlerMapping.setUrlMap(urlMap);
        return simpleUrlHandlerMapping;
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
