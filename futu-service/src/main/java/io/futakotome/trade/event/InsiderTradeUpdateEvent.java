package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.InsiderTradeListContent;

public class InsiderTradeUpdateEvent {
    private InsiderTradeListContent content;

    public InsiderTradeUpdateEvent(InsiderTradeListContent content) {
        this.content = content;
    }

    public InsiderTradeListContent getContent() {
        return content;
    }

    public void setContent(InsiderTradeListContent content) {
        this.content = content;
    }
}
