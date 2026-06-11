package io.futakotome.trade.event;

import io.futakotome.trade.dto.ws.AnalystConsensusContent;

public class AnalystConsensusUpdateEvent {
    private AnalystConsensusContent content;

    public AnalystConsensusUpdateEvent(AnalystConsensusContent content) {
        this.content = content;
    }

    public AnalystConsensusContent getContent() {
        return content;
    }

    public void setContent(AnalystConsensusContent content) {
        this.content = content;
    }
}
