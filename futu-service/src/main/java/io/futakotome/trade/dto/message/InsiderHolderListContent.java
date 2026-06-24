package io.futakotome.trade.dto.message;

import java.util.List;

public class InsiderHolderListContent {
    private List<OwnerInsiderHolderItem> itemList;
    private Integer allCount;  // 总数
    private String nextKey;  // 分页标识，"-1" 表示无更多数据
    private Integer insiderTotalCount;  // 内部人总人数，仅首次请求（nextKey为空）时返回
    private Integer insiderBoughtCount;  // 内部人买入总人数，仅首次请求时返回
    private Integer insiderSoldCount;  // 内部人卖出总人数，仅首次请求时返回

    public List<OwnerInsiderHolderItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<OwnerInsiderHolderItem> itemList) {
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

    public Integer getInsiderTotalCount() {
        return insiderTotalCount;
    }

    public void setInsiderTotalCount(Integer insiderTotalCount) {
        this.insiderTotalCount = insiderTotalCount;
    }

    public Integer getInsiderBoughtCount() {
        return insiderBoughtCount;
    }

    public void setInsiderBoughtCount(Integer insiderBoughtCount) {
        this.insiderBoughtCount = insiderBoughtCount;
    }

    public Integer getInsiderSoldCount() {
        return insiderSoldCount;
    }

    public void setInsiderSoldCount(Integer insiderSoldCount) {
        this.insiderSoldCount = insiderSoldCount;
    }
}
