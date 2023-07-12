package io.futakotome.trade.listener;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.futakotome.trade.listener.vo.RealTimeBaseQuoteMessage;
import io.futakotome.trade.listener.vo.RealTimeKLMessage;
import io.futakotome.trade.listener.vo.RealTimeTickerMessage;
import io.futakotome.trade.listener.vo.TimeShareMessage;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = JoinMessage.class, name = "JOIN_IN"),
        @JsonSubTypes.Type(value = NotifyMessage.class, name = "NOTIFY"),
        @JsonSubTypes.Type(value = RealTimeBaseQuoteMessage.class, name = "RT_BASIC_QUOTE"),
        @JsonSubTypes.Type(value = RealTimeKLMessage.class, name = "RT_KL"),
        @JsonSubTypes.Type(value = RealTimeTickerMessage.class, name = "RT_TICKER"),
        @JsonSubTypes.Type(value = TimeShareMessage.class, name = "RT_TIME_SHARE")
})
public interface Message {
    MessageType getType();
}
