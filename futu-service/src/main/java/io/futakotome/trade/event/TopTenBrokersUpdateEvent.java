package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.TopTenBrokersContent;

public class TopTenBrokersUpdateEvent {
    private TopTenBrokersContent content;

    public TopTenBrokersUpdateEvent(TopTenBrokersContent content) {
        this.content = content;
    }

    public TopTenBrokersContent getContent() {
        return content;
    }

    public void setContent(TopTenBrokersContent content) {
        this.content = content;
    }
}
