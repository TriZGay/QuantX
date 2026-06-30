package io.futakotome.trade.dto.message;

import java.util.List;

public class ArkFundHoldingContent {
    private List<ArkFundHoldingItem> dataList;   // 数据列表
    private Integer allCount;                // 总数
    private String nextPage;               // 下一页游标, 空=无更多

    public List<ArkFundHoldingItem> getDataList() {
        return dataList;
    }

    public void setDataList(List<ArkFundHoldingItem> dataList) {
        this.dataList = dataList;
    }

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }
}
