package io.futakotome.trade.dto.message;

import java.util.List;

public class IndustrialPlateStockContent {
    private List<StockInfo> stockList;  //成分股列表
    private String page;          //翻页标记，有更多数据时返回
    private Integer allCount;       //总数量

    public List<StockInfo> getStockList() {
        return stockList;
    }

    public void setStockList(List<StockInfo> stockList) {
        this.stockList = stockList;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }
}
