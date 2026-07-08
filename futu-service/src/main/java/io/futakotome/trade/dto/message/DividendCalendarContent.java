package io.futakotome.trade.dto.message;

import java.util.List;

public class DividendCalendarContent {
    private List<DividendCalendarItem> itemList;      // 派息列表
    private Integer allCount;                     // 该日期下的总数量

    public List<DividendCalendarItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<DividendCalendarItem> itemList) {
        this.itemList = itemList;
    }

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }
}
