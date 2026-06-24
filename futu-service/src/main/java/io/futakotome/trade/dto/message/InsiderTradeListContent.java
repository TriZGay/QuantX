package io.futakotome.trade.dto.message;

import java.util.List;

public class InsiderTradeListContent {
    private List<OwnerInsiderTradeItem> itemList;
    private Integer allCount; // 总数
    private String nextKey; // 分页标识，"-1" 表示无更多数据

    public List<OwnerInsiderTradeItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<OwnerInsiderTradeItem> itemList) {
        this.itemList = itemList;
    }

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public String getNextKey() {
        return nextKey;
    }

    public void setNextKey(String nextKey) {
        this.nextKey = nextKey;
    }
}
