package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.ShareholderInstitutionalContent;

public class ShareholderInstitutionalUpdateEvent {
    private ShareholderInstitutionalContent content;

    public ShareholderInstitutionalUpdateEvent(ShareholderInstitutionalContent content) {
        this.content = content;
    }

    public ShareholderInstitutionalContent getContent() {
        return content;
    }

    public void setContent(ShareholderInstitutionalContent content) {
        this.content = content;
    }
}
