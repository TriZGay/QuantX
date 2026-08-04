package io.futakotome.trade.dto.message;

import java.util.List;

public class IndicatorListContent {
    private List<IndicatorEntry> indicatorList;

    public List<IndicatorEntry> getIndicatorList() {
        return indicatorList;
    }

    public void setIndicatorList(List<IndicatorEntry> indicatorList) {
        this.indicatorList = indicatorList;
    }
}
