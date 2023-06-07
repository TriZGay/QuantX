package io.futakotome.trade.listener;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = FTJoinMessage.class, name = "JOIN_IN"),
        @JsonSubTypes.Type(value = NotifyMessage.class, name = "NOTIFY"),
        @JsonSubTypes.Type(value = RealTimeBaseQuoteMessage.class, name = "RT_BASIC_QUOTE")
})
public interface Message {
    MessageType getType();
}
