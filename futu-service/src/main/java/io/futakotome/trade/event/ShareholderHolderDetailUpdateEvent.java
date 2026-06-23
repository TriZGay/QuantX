package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.ShareholderHolderDetailContent;

public class ShareholderHolderDetailUpdateEvent {
    private ShareholderHolderDetailContent content;

    public ShareholderHolderDetailUpdateEvent(ShareholderHolderDetailContent content) {
        this.content = content;
    }

    public ShareholderHolderDetailContent getContent() {
        return content;
    }

    public void setContent(ShareholderHolderDetailContent content) {
        this.content = content;
    }
}
