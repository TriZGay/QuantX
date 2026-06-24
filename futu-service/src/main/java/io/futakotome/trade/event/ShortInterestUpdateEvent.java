package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.ShortInterestContent;

public class ShortInterestUpdateEvent {
    private ShortInterestContent content;

    public ShortInterestUpdateEvent(ShortInterestContent content) {
        this.content = content;
    }

    public ShortInterestContent getContent() {
        return content;
    }

    public void setContent(ShortInterestContent content) {
        this.content = content;
    }
}
