package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.EarningsBeatRankContent;

public class EarningsBeatRankUpdateEvent {
    private EarningsBeatRankContent content;

    public EarningsBeatRankUpdateEvent(EarningsBeatRankContent content) {
        this.content = content;
    }

    public EarningsBeatRankContent getContent() {
        return content;
    }

    public void setContent(EarningsBeatRankContent content) {
        this.content = content;
    }
}
