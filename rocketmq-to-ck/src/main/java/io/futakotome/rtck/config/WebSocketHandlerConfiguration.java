package io.futakotome.rtck.config;

import io.futakotome.rtck.annotation.WebSocketMappingAnnotationHandler;
import io.futakotome.rtck.message.core.WebSocketSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class WebSocketHandlerConfiguration {
    public static final String NOTIFY_PATH = "/websocket/notify";
    public static final String KLINE_PATH = "/websocket/kl";
    public static final String MARKET_STATE_PATH = "/websocket/ms";
    public static final String BASIC_QUOTE_PATH = "/websocket/bq";
//
//    private final ReactiveWebSocketNotifyServerHandler notifyHandler;
//    private final ReactiveWebSocketKLineServerHandler kLineHandler;
//    private final ReactiveWebSocketMarketStateServerHandler marketStateServerHandler;
//    private final ReactiveWebSocketBasicQuoteServerHandler basicQuoteServerHandle;
//
//    public WebSocketHandlerConfiguration(ReactiveWebSocketNotifyServerHandler notifyHandler, ReactiveWebSocketKLineServerHandler kLineHandler, ReactiveWebSocketMarketStateServerHandler marketStateServerHandler, ReactiveWebSocketBasicQuoteServerHandler basicQuoteServerHandle) {
//        this.notifyHandler = notifyHandler;
//        this.kLineHandler = kLineHandler;
//        this.marketStateServerHandler = marketStateServerHandler;
//        this.basicQuoteServerHandle = basicQuoteServerHandle;
//    }

    @Bean
    public HandlerMapping webSocketHandlerMapping() {
//        Map<String, WebSocketHandler> urlMap = new HashMap<>() {{
//            put(NOTIFY_PATH, notifyHandler);
//            put(KLINE_PATH, kLineHandler);
//            put(MARKET_STATE_PATH, marketStateServerHandler);
//            put(BASIC_QUOTE_PATH, basicQuoteServerHandle);
//        }};
//        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
//        simpleUrlHandlerMapping.setOrder(1);
//        simpleUrlHandlerMapping.setUrlMap(urlMap);
//        return simpleUrlHandlerMapping;
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
