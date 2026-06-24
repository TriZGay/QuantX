package io.futakotome.trade.dto.message;

import java.util.List;

public class ShortInterestContent {
    private List<UsShortInterestItem> usItemList; // 美股空头持仓数据列表
    private List<HkShortInterestItem> hkItemList; // 港股空头持仓数据列表
    private String nextKey; // 分页标识，"-1" 表示无更多数据

    public List<UsShortInterestItem> getUsItemList() {
        return usItemList;
    }

    public void setUsItemList(List<UsShortInterestItem> usItemList) {
        this.usItemList = usItemList;
    }

    public List<HkShortInterestItem> getHkItemList() {
        return hkItemList;
    }

    public void setHkItemList(List<HkShortInterestItem> hkItemList) {
        this.hkItemList = hkItemList;
    }

    public String getNextKey() {
        return nextKey;
    }

    public void setNextKey(String nextKey) {
        this.nextKey = nextKey;
    }
}
