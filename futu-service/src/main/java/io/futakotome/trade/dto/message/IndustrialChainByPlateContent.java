package io.futakotome.trade.dto.message;

import java.util.List;

public class IndustrialChainByPlateContent {
    private List<RelatedChainInfo> relatedChainList;    // 关联的产业链

    public List<RelatedChainInfo> getRelatedChainList() {
        return relatedChainList;
    }

    public void setRelatedChainList(List<RelatedChainInfo> relatedChainList) {
        this.relatedChainList = relatedChainList;
    }
}
