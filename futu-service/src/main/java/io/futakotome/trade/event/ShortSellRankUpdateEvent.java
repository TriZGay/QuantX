package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.ShortSellRankContent;

public class ShortSellRankUpdateEvent {
    private ShortSellRankContent content;

    public ShortSellRankUpdateEvent(ShortSellRankContent content) {
        this.content = content;
    }

    public ShortSellRankContent getContent() {
        return content;
    }

    public void setContent(ShortSellRankContent content) {
        this.content = content;
    }
}
