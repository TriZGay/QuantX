package io.futakotome.trade.dto.ws;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ConnectWsMessage.class, name = "CONNECT"),
        @JsonSubTypes.Type(value = MarketStateWsMessage.class, name = "MARKET_STATE"),
        @JsonSubTypes.Type(value = HistoryKLDetailWsMessage.class, name = "KL_HISTORY_DETAIL"),
        @JsonSubTypes.Type(value = SubOrUnSubWsMessage.class, name = "SUBSCRIPTION"),
        @JsonSubTypes.Type(value = HistoryKLWsMessage.class, name = "KL_HISTORY"),
        @JsonSubTypes.Type(value = PlatesWsMessage.class, name = "PLATES"),
        @JsonSubTypes.Type(value = StockInPlateWsMessage.class, name = "STOCK_IN_PLATE"),
        @JsonSubTypes.Type(value = StocksWsMessage.class, name = "STOCKS"),
        @JsonSubTypes.Type(value = StockOwnerPlateWsMessage.class, name = "STOCK_OWNER_PLATE"),
        @JsonSubTypes.Type(value = CapitalFlowWsMessage.class, name = "CAPITAL_FLOW"),
        @JsonSubTypes.Type(value = CapitalDistributionWsMessage.class, name = "CAPITAL_DISTRIBUTION"),
        @JsonSubTypes.Type(value = RehabsWsMessage.class, name = "REHABS"),
        @JsonSubTypes.Type(value = SnapshotWsMessage.class, name = "SNAPSHOT"),
        @JsonSubTypes.Type(value = AccountsWsMessage.class, name = "ACCOUNTS"),

        @JsonSubTypes.Type(value = RealTimeBaseQuoteMessage.class, name = "RT_BASIC_QUOTE"),
        @JsonSubTypes.Type(value = RealTimeKLMessage.class, name = "RT_KL"),
        @JsonSubTypes.Type(value = RealTimeTickerMessage.class, name = "RT_TICKER"),
        @JsonSubTypes.Type(value = TimeShareMessage.class, name = "RT_TIME_SHARE"),
        @JsonSubTypes.Type(value = BrokerMessage.class, name = "RT_BROKERS")
})
public interface Message {
    MessageType getType();
}
