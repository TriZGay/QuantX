package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.HotListContent;

public class HotListUpdateEvent {
    public HotListContent content;

    public HotListUpdateEvent(HotListContent content) {
        this.content = content;
    }

    public HotListContent getContent() {
        return content;
    }

    public void setContent(HotListContent content) {
        this.content = content;
    }
}
