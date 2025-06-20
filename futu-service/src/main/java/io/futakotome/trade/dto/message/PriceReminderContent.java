package io.futakotome.trade.dto.message;

import java.util.List;

public class PriceReminderContent {
    private List<PriceReminder> priceReminderList;

    public List<PriceReminder> getPriceReminderList() {
        return priceReminderList;
    }

    public void setPriceReminderList(List<PriceReminder> priceReminderList) {
        this.priceReminderList = priceReminderList;
    }
}
