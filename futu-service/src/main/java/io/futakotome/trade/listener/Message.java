package io.futakotome.trade.listener;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = FTJoinMessage.class, name = "JOIN_IN"),
        @JsonSubTypes.Type(value = NotifyMessage.class, name = "NOTIFY")
})
public interface Message {
    MessageType getType();
}
