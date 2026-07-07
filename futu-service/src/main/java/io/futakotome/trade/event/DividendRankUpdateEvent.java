package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.DividendRankContent;

public class DividendRankUpdateEvent {
    private DividendRankContent content;

    public DividendRankUpdateEvent(DividendRankContent content) {
        this.content = content;
    }

    public DividendRankContent getContent() {
        return content;
    }

    public void setContent(DividendRankContent content) {
        this.content = content;
    }
}
