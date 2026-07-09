package io.futakotome.trade.dto.message;

import java.util.List;

public class EarningsBeatRankContent {
    private List<EarningsBeatItem> dataList;     // 数据列表
    private Integer allCount;                // 数据总量

    public List<EarningsBeatItem> getDataList() {
        return dataList;
    }

    public void setDataList(List<EarningsBeatItem> dataList) {
        this.dataList = dataList;
    }

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }
}
