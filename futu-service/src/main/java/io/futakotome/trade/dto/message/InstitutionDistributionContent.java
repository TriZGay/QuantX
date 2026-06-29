package io.futakotome.trade.dto.message;

import java.util.List;

public class InstitutionDistributionContent {
    private List<IndustryDistributionItem> dataList ; // 数据列表

    public List<IndustryDistributionItem> getDataList() {
        return dataList;
    }

    public void setDataList(List<IndustryDistributionItem> dataList) {
        this.dataList = dataList;
    }
}
