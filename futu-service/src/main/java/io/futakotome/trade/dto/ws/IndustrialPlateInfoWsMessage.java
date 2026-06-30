package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.IndustrialPlateInfoContent;

public class IndustrialPlateInfoWsMessage implements Message {
    private Long plateId;             // 产业板块ID
    private IndustrialPlateInfoContent content;

    public Long getPlateId() {
        return plateId;
    }

    public void setPlateId(Long plateId) {
        this.plateId = plateId;
    }

    public IndustrialPlateInfoContent getContent() {
        return content;
    }

    public void setContent(IndustrialPlateInfoContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.INDUSTRIAL_PLATE_INFO;
    }
}
