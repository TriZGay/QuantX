package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.InsiderHolderListContent;

public class InsiderHolderListUpdateEvent {
    private InsiderHolderListContent content;

    public InsiderHolderListUpdateEvent(InsiderHolderListContent content) {
        this.content = content;
    }

    public InsiderHolderListContent getContent() {
        return content;
    }

    public void setContent(InsiderHolderListContent content) {
        this.content = content;
    }
}
