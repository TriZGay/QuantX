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
        @JsonSubTypes.Type(value = RealTimeBaseQuoteMessage.class, name = "RT_BASIC_QUOTE"),
        @JsonSubTypes.Type(value = RealTimeKLMessage.class, name = "RT_KL"),
        @JsonSubTypes.Type(value = RealTimeTickerMessage.class, name = "RT_TICKER"),
        @JsonSubTypes.Type(value = TimeShareMessage.class, name = "RT_TIME_SHARE"),
        @JsonSubTypes.Type(value = BrokerMessage.class, name = "RT_BROKERS")
})
public interface Message {
    MessageType getType();
}
