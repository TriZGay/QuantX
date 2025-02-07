package io.futakotome.trade.controller.ws;

import io.futakotome.trade.dto.ws.HistoryKLDetailWsMessage;
import io.futakotome.trade.dto.ws.MarketStateWsMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static io.futakotome.trade.config.WebSocketMessageBrokerConfig.BROKER_PREFIX;
import static io.futakotome.trade.controller.ws.QuantxWsController.*;

@Service
public class QuantxFutuWsService {
    private final SimpMessagingTemplate template;

    public QuantxFutuWsService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void sendNotify(String msg) {
        this.template.convertAndSend(BROKER_PREFIX + NOTIFY_URI, msg);
    }

    public void sendHistoryKQuotaDetails(HistoryKLDetailWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + HISTORY_KLINE_QUOTA_URI, message);
    }

    public void sendMarketStart(MarketStateWsMessage marketStateMessage) {
        this.template.convertAndSend(BROKER_PREFIX + MARKET_STATE_URI, marketStateMessage);
    }
}
