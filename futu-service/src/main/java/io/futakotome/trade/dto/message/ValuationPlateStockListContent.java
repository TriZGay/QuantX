package io.futakotome.trade.dto.message;

import java.util.List;

public class ValuationPlateStockListContent {
    private Integer count;  // 成分股总数
    private List<StockItem> stockList;  // 成分股估值列表
    private String nextKey;  // 分页标识，"-1" 表示无更多数据
    private List<PlateItem> plateList;  // 指数成分股所属行业/板块列表；仅在指数全部板块首次请求时返回

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<StockItem> getStockList() {
        return stockList;
    }

    public void setStockList(List<StockItem> stockList) {
        this.stockList = stockList;
    }

    public String getNextKey() {
        return nextKey;
    }

    public void setNextKey(String nextKey) {
        this.nextKey = nextKey;
    }

    public List<PlateItem> getPlateList() {
        return plateList;
    }

    public void setPlateList(List<PlateItem> plateList) {
        this.plateList = plateList;
    }
}
