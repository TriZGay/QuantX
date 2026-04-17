package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.CommonStaticInfo;
import io.futakotome.trade.dto.message.StockContent;

import java.util.List;

public class StaticInfoUpdateEvent {
    private CommonStaticInfo staticInfo;
    private List<StockContent> stockContents;

    public StaticInfoUpdateEvent() {
    }

    public StaticInfoUpdateEvent(CommonStaticInfo staticInfo, List<StockContent> stockContents) {
        this.staticInfo = staticInfo;
        this.stockContents = stockContents;
    }

    public CommonStaticInfo getStaticInfo() {
        return staticInfo;
    }

    public void setStaticInfo(CommonStaticInfo staticInfo) {
        this.staticInfo = staticInfo;
    }

    public List<StockContent> getStockContents() {
        return stockContents;
    }

    public void setStockContents(List<StockContent> stockContents) {
        this.stockContents = stockContents;
    }
}
