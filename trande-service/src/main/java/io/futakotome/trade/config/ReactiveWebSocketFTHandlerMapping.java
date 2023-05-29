package io.futakotome.trade.config;

import io.futakotome.trade.service.FTQotService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Component
public class ReactiveWebSocketFTHandlerMapping extends SimpleUrlHandlerMapping {

    public ReactiveWebSocketFTHandlerMapping(FTQotService ftQotService) {
        Map<String, WebSocketHandler> handlerMap = new HashMap<>();
        handlerMap.put("/websocket/**", new ReactiveWebSocketNotifyServerHandler(ftQotService));
        setUrlMap(handlerMap);
        setOrder(100);
    }
}
