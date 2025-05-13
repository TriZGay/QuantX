package io.futakotome.trade.dto.message;

import java.util.List;

public class StockFilterContent {
    private boolean lastPage;
    private Integer allCount;
    private List<StockData> dataList;

    public List<StockData> getDataList() {
        return dataList;
    }

    public void setDataList(List<StockData> dataList) {
        this.dataList = dataList;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

}
