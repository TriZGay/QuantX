package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.TopMoversRankContent;

public class TopMoversRankUpdateEvent {
    private TopMoversRankContent content;

    public TopMoversRankUpdateEvent(TopMoversRankContent content) {
        this.content = content;
    }

    public TopMoversRankContent getContent() {
        return content;
    }

    public void setContent(TopMoversRankContent content) {
        this.content = content;
    }
}
