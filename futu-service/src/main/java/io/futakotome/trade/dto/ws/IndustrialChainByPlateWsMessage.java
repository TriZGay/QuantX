package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.IndustrialChainByPlateContent;

public class IndustrialChainByPlateWsMessage implements Message {
    private Long plateId;             // 产业板块ID
    private IndustrialChainByPlateContent content;

    public Long getPlateId() {
        return plateId;
    }

    public void setPlateId(Long plateId) {
        this.plateId = plateId;
    }

    public IndustrialChainByPlateContent getContent() {
        return content;
    }

    public void setContent(IndustrialChainByPlateContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.INDUSTRIAL_CHAIN_BY_PLATE;
    }
}
