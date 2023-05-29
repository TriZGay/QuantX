package io.futakotome.trade.config;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Component
public class ReactiveWebSocketFTHandlerMapping extends SimpleUrlHandlerMapping {
    public ReactiveWebSocketFTHandlerMapping() {
        Map<String, WebSocketHandler> handlerMap = new HashMap<>();
        handlerMap.put("/websocket/**", new ReactiveWebSocketServerHandler());
        setUrlMap(handlerMap);
        setOrder(100);
    }
}
