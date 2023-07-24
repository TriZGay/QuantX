package io.futakotome.rtck.message;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ReactiveWebSocketHandlerMapping {
    public static final String NOTIFY_PATH = "/websocket/notify";
    public static final String KLINE_PATH = "/websocket/kl";
    public static final String MARKET_STATE_PATH = "/websocket/ms";

    private final ReactiveWebSocketNotifyServerHandler notifyHandler;
    private final ReactiveWebSocketKLineServerHandler kLineHandler;
    private final ReactiveWebSocketMarketStateServerHandler marketStateServerHandler;

    public ReactiveWebSocketHandlerMapping(ReactiveWebSocketNotifyServerHandler notifyHandler, ReactiveWebSocketKLineServerHandler kLineHandler, ReactiveWebSocketMarketStateServerHandler marketStateServerHandler) {
        this.notifyHandler = notifyHandler;
        this.kLineHandler = kLineHandler;
        this.marketStateServerHandler = marketStateServerHandler;
    }

    @Bean
    public HandlerMapping webSocketHandlerMapping() {
        Map<String, WebSocketHandler> urlMap = new HashMap<>() {{
            put(NOTIFY_PATH, notifyHandler);
            put(KLINE_PATH, kLineHandler);
            put(MARKET_STATE_PATH, marketStateServerHandler);
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
