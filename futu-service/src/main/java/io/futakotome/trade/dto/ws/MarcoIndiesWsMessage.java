package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.MarcoIndiesContent;

public class MarcoIndiesWsMessage implements Message {
    private Integer region; //MacroRegion, 国家/地区
    private MarcoIndiesContent content;

    public Integer getRegion() {
        return region;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }

    public MarcoIndiesContent getContent() {
        return content;
    }

    public void setContent(MarcoIndiesContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.MARCO_INDIES;
    }
}
