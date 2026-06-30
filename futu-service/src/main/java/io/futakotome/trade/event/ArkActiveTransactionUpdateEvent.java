package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.ArkActiveTransactionContent;

public class ArkActiveTransactionUpdateEvent {
    private ArkActiveTransactionContent content;

    public ArkActiveTransactionUpdateEvent(ArkActiveTransactionContent content) {
        this.content = content;
    }

    public ArkActiveTransactionContent getContent() {
        return content;
    }

    public void setContent(ArkActiveTransactionContent content) {
        this.content = content;
    }
}
