package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.ShareholderHoldingChangeContent;

public class ShareholderHoldingChangeUpdateEvent {
    private ShareholderHoldingChangeContent content;

    public ShareholderHoldingChangeUpdateEvent(ShareholderHoldingChangeContent content) {
        this.content = content;
    }

    public ShareholderHoldingChangeContent getContent() {
        return content;
    }

    public void setContent(ShareholderHoldingChangeContent content) {
        this.content = content;
    }
}
