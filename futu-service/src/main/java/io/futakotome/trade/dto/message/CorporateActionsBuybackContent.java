package io.futakotome.trade.dto.message;

import java.util.List;

public class CorporateActionsBuybackContent {
    private List<HKBuyBackItem> hkBuyBackList;
    private List<ABuyBackItem> aBuyBackList;
    private String nextKey;

    public List<HKBuyBackItem> getHkBuyBackList() {
        return hkBuyBackList;
    }

    public void setHkBuyBackList(List<HKBuyBackItem> hkBuyBackList) {
        this.hkBuyBackList = hkBuyBackList;
    }

    public List<ABuyBackItem> getaBuyBackList() {
        return aBuyBackList;
    }

    public void setaBuyBackList(List<ABuyBackItem> aBuyBackList) {
        this.aBuyBackList = aBuyBackList;
    }

    public String getNextKey() {
        return nextKey;
    }

    public void setNextKey(String nextKey) {
        this.nextKey = nextKey;
    }
}
