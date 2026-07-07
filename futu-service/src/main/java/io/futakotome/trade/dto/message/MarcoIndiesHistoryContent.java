package io.futakotome.trade.dto.message;

import java.util.List;

public class MarcoIndiesHistoryContent {
    private Long indicatorId;           //指标ID(回显)
    private List<MacroDataPoint> dataList;      //历史数据点列表(按时间降序)

    public Long getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(Long indicatorId) {
        this.indicatorId = indicatorId;
    }

    public List<MacroDataPoint> getDataList() {
        return dataList;
    }

    public void setDataList(List<MacroDataPoint> dataList) {
        this.dataList = dataList;
    }
}
