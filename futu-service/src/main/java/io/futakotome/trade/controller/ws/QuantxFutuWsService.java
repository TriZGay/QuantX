package io.futakotome.trade.controller.ws;

import io.futakotome.common.message.RTEmaMessage;
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

    public void sendAccounts(AccountsWsMessage accountsWsMessage) {
        this.template.convertAndSend(BROKER_PREFIX + ACCOUNTS_URI, accountsWsMessage);
    }

    public void sendAccPosition(AccPositionWsMessage accPositionWsMessage) {
        this.template.convertAndSend(BROKER_PREFIX + POSITION_URI, accPositionWsMessage);
    }

    public void sendStockFilterMessage(StockFilterWsMessage wsMessage) {
        this.template.convertAndSend(BROKER_PREFIX + STOCK_FILTER_URL, wsMessage);
    }

    public void sendAccFundsMessage(AccFundsWsMessage accFundsWsMessage) {
        this.template.convertAndSend(BROKER_PREFIX + ACC_FUNDS_URI, accFundsWsMessage);
    }

    public void sendHistoryOrders(HistoryOrdersWsMessage historyOrdersWsMessage) {
        this.template.convertAndSend(BROKER_PREFIX + HISTORY_ORDER_URI, historyOrdersWsMessage);
    }

    public void sendIncompleteOrders(IncompleteOrdersWsMessage incompleteOrdersWsMessage) {
        this.template.convertAndSend(BROKER_PREFIX + INCOMPLETE_ORDER_URI, incompleteOrdersWsMessage);
    }

    public void sendUserGroup(UserGroupWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + USER_GROUP_URI, message);
    }

    public void sendUserSecurity(UserSecurityWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + USER_SECURITY_URI, message);
    }

    public void sendGetPriceReminderListMessage(GetPriceReminderWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + GET_PRICE_REMINDER_URI, message);
    }

    public void sendRtEma5(RTEmaMessage emaMessage) {
        this.template.convertAndSend(BROKER_PREFIX + EMA5_URI, emaMessage);
    }

}
