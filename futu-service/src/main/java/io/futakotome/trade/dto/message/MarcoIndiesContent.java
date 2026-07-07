package io.futakotome.trade.dto.message;

import java.util.List;

public class MarcoIndiesContent {
    private List<MacroIndicatorCategory> indicatorList ; //分类指标列表

    public List<MacroIndicatorCategory> getIndicatorList() {
        return indicatorList;
    }

    public void setIndicatorList(List<MacroIndicatorCategory> indicatorList) {
        this.indicatorList = indicatorList;
    }
}
