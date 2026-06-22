package io.futakotome.trade.dto.message;

import java.util.List;

public class ShareholderHoldingChangeContent {
    private List<OwnerListItem> itemList;  // 持股变动记录列表
    private String nextKey;  // 分页标识，"-1" 表示无更多数据

    public List<OwnerListItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<OwnerListItem> itemList) {
        this.itemList = itemList;
    }

    public String getNextKey() {
        return nextKey;
    }

    public void setNextKey(String nextKey) {
        this.nextKey = nextKey;
    }
}
