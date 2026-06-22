package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.ShareholderOvrContent;

public class ShareholderOvrUpdateEvent {
    private ShareholderOvrContent content;

    public ShareholderOvrUpdateEvent(ShareholderOvrContent content) {
        this.content = content;
    }

    public ShareholderOvrContent getContent() {
        return content;
    }

    public void setContent(ShareholderOvrContent content) {
        this.content = content;
    }
}
