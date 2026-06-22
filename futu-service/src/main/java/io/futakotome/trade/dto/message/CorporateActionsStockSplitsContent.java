package io.futakotome.trade.dto.message;

import java.util.List;

public class CorporateActionsStockSplitsContent {
    private List<StockSplitItem> splitItemList;
    private String nextKey;

    public List<StockSplitItem> getSplitItemList() {
        return splitItemList;
    }

    public void setSplitItemList(List<StockSplitItem> splitItemList) {
        this.splitItemList = splitItemList;
    }

    public String getNextKey() {
        return nextKey;
    }

    public void setNextKey(String nextKey) {
        this.nextKey = nextKey;
    }
}
