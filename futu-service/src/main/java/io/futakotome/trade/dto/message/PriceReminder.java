package io.futakotome.trade.dto.message;

import java.util.List;

public class PriceReminder {
    private CommonSecurity security;
    private String name;
    private List<PriceReminderItem> itemList;

    public CommonSecurity getSecurity() {
        return security;
    }

    public void setSecurity(CommonSecurity security) {
        this.security = security;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PriceReminderItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<PriceReminderItem> itemList) {
        this.itemList = itemList;
    }
}
