package io.futakotome.trade.listener;

import io.futakotome.trade.service.FTQotService;
import io.futakotome.trade.service.FTTradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ReactiveWebSocketListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveWebSocketListener.class);
    private final FTQotService ftQotService;
    private final FTTradeService ftTradeService;
    public ReactiveWebSocketListener(FTQotService ftQotService, FTTradeService ftTradeService) {
        this.ftQotService = ftQotService;
        this.ftTradeService = ftTradeService;
    }

    public void onMessage(Message message, String sessionId) {
        LOGGER.info("message:" + message.getType());
        MessageType type = message.getType();
        switch (type) {
            case JOIN_IN: {
                ftQotService.setSessionId(sessionId);
                ftTradeService.setSessionId(sessionId);
            }
        }
    }
}
