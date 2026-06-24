package io.futakotome.trade.dto.message;

import java.util.List;

public class CompanyProfileContent {
    private List<CompanyLabItem> itemList; // 公司详情标签列表

    public List<CompanyLabItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<CompanyLabItem> itemList) {
        this.itemList = itemList;
    }
}
