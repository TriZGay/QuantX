package io.futakotome.trade.dto.message;

import java.util.List;

public class OwnershipStaticInfo {
    private Long staticDate; // 统计日期时间戳（秒）
    private String staticDateStr; // 统计日期字符串，格式 YYYY-MM-DD，对应市场时区
    private List<OwnershipStaticItem> itemList; // 持股统计子项列表

    public Long getStaticDate() {
        return staticDate;
    }

    public void setStaticDate(Long staticDate) {
        this.staticDate = staticDate;
    }

    public String getStaticDateStr() {
        return staticDateStr;
    }

    public void setStaticDateStr(String staticDateStr) {
        this.staticDateStr = staticDateStr;
    }

    public List<OwnershipStaticItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<OwnershipStaticItem> itemList) {
        this.itemList = itemList;
    }
}
