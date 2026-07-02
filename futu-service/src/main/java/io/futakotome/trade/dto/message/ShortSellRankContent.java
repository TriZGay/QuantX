package io.futakotome.trade.dto.message;

import java.util.List;

public class ShortSellRankContent {
    private List<ShortSellingRankItem> dataList;  // 数据列表
    private Integer allCount;                 // 符合条件的总数据量

    public List<ShortSellingRankItem> getDataList() {
        return dataList;
    }

    public void setDataList(List<ShortSellingRankItem> dataList) {
        this.dataList = dataList;
    }

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }
}
