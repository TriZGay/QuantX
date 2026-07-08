package io.futakotome.trade.dto.message;

import java.util.List;

public class EarningsCalendarContent {
    private List<EarningsCalendarItem> itemList;

    public List<EarningsCalendarItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<EarningsCalendarItem> itemList) {
        this.itemList = itemList;
    }
}
