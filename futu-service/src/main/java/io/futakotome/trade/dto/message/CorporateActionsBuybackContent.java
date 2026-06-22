package io.futakotome.trade.dto.message;

import java.util.List;

public class CorporateActionsBuybackContent {
    private List<HKBuyBackItem> hkBuyBackItems;
    private List<ABuyBackItem> aBuyBackItems;
    private String nextKey;

    public List<HKBuyBackItem> getHkBuyBackItems() {
        return hkBuyBackItems;
    }

    public void setHkBuyBackItems(List<HKBuyBackItem> hkBuyBackItems) {
        this.hkBuyBackItems = hkBuyBackItems;
    }

    public List<ABuyBackItem> getaBuyBackItems() {
        return aBuyBackItems;
    }

    public void setaBuyBackItems(List<ABuyBackItem> aBuyBackItems) {
        this.aBuyBackItems = aBuyBackItems;
    }

    public String getNextKey() {
        return nextKey;
    }

    public void setNextKey(String nextKey) {
        this.nextKey = nextKey;
    }
}
