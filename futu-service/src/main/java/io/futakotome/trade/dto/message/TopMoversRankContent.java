package io.futakotome.trade.dto.message;

import java.util.List;

public class TopMoversRankContent {
    private List<TopMoversRankItem> dataList;    // 数据列表
    private Integer allCount;                // 符合条件的总数据量

    public List<TopMoversRankItem> getDataList() {
        return dataList;
    }

    public void setDataList(List<TopMoversRankItem> dataList) {
        this.dataList = dataList;
    }

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }
}
