package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.HighDividendSoeRankContent;

public class HighDividendSoeRankUpdateEvent {
    private HighDividendSoeRankContent content;

    public HighDividendSoeRankUpdateEvent(HighDividendSoeRankContent content) {
        this.content = content;
    }

    public HighDividendSoeRankContent getContent() {
        return content;
    }

    public void setContent(HighDividendSoeRankContent content) {
        this.content = content;
    }
}
