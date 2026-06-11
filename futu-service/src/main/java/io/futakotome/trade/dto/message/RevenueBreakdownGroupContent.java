package io.futakotome.trade.dto.message;

import com.google.gson.annotations.JsonAdapter;
import io.futakotome.trade.utils.converter.RevenueBreakdownGroupConverter;

import java.util.List;

@JsonAdapter(RevenueBreakdownGroupConverter.class)
public class RevenueBreakdownGroupContent {
    private Integer type;
    private String typeStr;
    private List<MainIncomeItemContent> itemList;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public List<MainIncomeItemContent> getItemList() {
        return itemList;
    }

    public void setItemList(List<MainIncomeItemContent> itemList) {
        this.itemList = itemList;
    }
}
