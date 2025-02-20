package io.futakotome.trade.controller.ws;

import io.futakotome.trade.dto.ws.*;
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

    public void sendMarketState(MarketStateWsMessage marketStateMessage) {
        this.template.convertAndSend(BROKER_PREFIX + MARKET_STATE_URI, marketStateMessage);
    }

    public void sendCapitalDistribution(CapitalDistributionWsMessage capitalDistributionMessage) {
        this.template.convertAndSend(BROKER_PREFIX + CAPITAL_DISTR_URI, capitalDistributionMessage);
    }

    public void sendCapitalFlow(CapitalFlowWsMessage capitalFlowWsMessage) {
        this.template.convertAndSend(BROKER_PREFIX + CAPITAL_FLOW_URI, capitalFlowWsMessage);
    }

    public void sendRehabs(RehabsWsMessage rehabsWsMessage) {
        this.template.convertAndSend(BROKER_PREFIX + REHABS_URI, rehabsWsMessage);
    }
}
