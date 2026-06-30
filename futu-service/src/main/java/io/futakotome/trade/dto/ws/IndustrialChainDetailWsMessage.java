package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.IndustrialChainDetailContent;

public class IndustrialChainDetailWsMessage implements Message {
    private Long chainId;         // 产业链ID
    private IndustrialChainDetailContent content;

    public Long getChainId() {
        return chainId;
    }

    public void setChainId(Long chainId) {
        this.chainId = chainId;
    }

    public IndustrialChainDetailContent getContent() {
        return content;
    }

    public void setContent(IndustrialChainDetailContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.INDUSTRIAL_CHAIN_DETAIL;
    }
}
