package io.futakotome.trade.dto.message;

import java.util.List;

public class ShareholderHolderDetailContent {
    private Long updateTime;           // 数据更新时间戳（秒）
    private String updateTimeStr;        // 数据更新时间字符串，格式 YYYY-MM-DD HH:MM:SS，对应市场时区
    private String nextKey;              // 分页标识，"-1" 表示无更多数据
    private List<OwnershipDetailItem> itemList;

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public String getNextKey() {
        return nextKey;
    }

    public void setNextKey(String nextKey) {
        this.nextKey = nextKey;
    }

    public List<OwnershipDetailItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<OwnershipDetailItem> itemList) {
        this.itemList = itemList;
    }
}
