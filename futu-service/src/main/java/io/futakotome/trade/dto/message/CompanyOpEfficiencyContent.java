package io.futakotome.trade.dto.message;

import java.util.List;

public class CompanyOpEfficiencyContent {
    private List<OperationalEfficiencyItem> itemList; // 经营效率数据列表
    private String nextKey; //分页标识，"-1" 表示无更多数据
    private String currencyCode; // 货币代码(ISO 4217)

    public List<OperationalEfficiencyItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<OperationalEfficiencyItem> itemList) {
        this.itemList = itemList;
    }

    public String getNextKey() {
        return nextKey;
    }

    public void setNextKey(String nextKey) {
        this.nextKey = nextKey;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
