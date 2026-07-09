package io.futakotome.trade.dto.message;

import java.util.List;

public class EconomicCalendarContent {
    private List<EconomicCalendarItem> itemList; // 经济数据事件列表
    private String nextPage;               // 下一页翻页标记
    private Boolean hasMore;                  // 是否还有更多数据

    public List<EconomicCalendarItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<EconomicCalendarItem> itemList) {
        this.itemList = itemList;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }
}
