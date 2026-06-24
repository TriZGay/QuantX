package io.futakotome.trade.dto.message;

import java.util.List;

public class DailyShotVolumeContent {
    private List<UsDailyShortVolumeItem> usItemList; // 美股每日卖空数据列表
    private List<HkDailyShortVolumeItem> hkItemList; // 港股每日卖空数据列表
    private String nextKey; // 分页标识，"-1" 表示无更多数据
    private Long aggregatedShort; // 未平仓股数，仅港股
    private Double aggregatedShortRatio; // 占流通股比例，百分号前的值，如 12.34 表示 12.34%，仅港股
    private String newTimeStr; // 最新数据时间字符串，格式 YYYY-MM-DD，对应市场时区，仅港股

    public List<UsDailyShortVolumeItem> getUsItemList() {
        return usItemList;
    }

    public void setUsItemList(List<UsDailyShortVolumeItem> usItemList) {
        this.usItemList = usItemList;
    }

    public List<HkDailyShortVolumeItem> getHkItemList() {
        return hkItemList;
    }

    public void setHkItemList(List<HkDailyShortVolumeItem> hkItemList) {
        this.hkItemList = hkItemList;
    }

    public String getNextKey() {
        return nextKey;
    }

    public void setNextKey(String nextKey) {
        this.nextKey = nextKey;
    }

    public Long getAggregatedShort() {
        return aggregatedShort;
    }

    public void setAggregatedShort(Long aggregatedShort) {
        this.aggregatedShort = aggregatedShort;
    }

    public Double getAggregatedShortRatio() {
        return aggregatedShortRatio;
    }

    public void setAggregatedShortRatio(Double aggregatedShortRatio) {
        this.aggregatedShortRatio = aggregatedShortRatio;
    }

    public String getNewTimeStr() {
        return newTimeStr;
    }

    public void setNewTimeStr(String newTimeStr) {
        this.newTimeStr = newTimeStr;
    }
}
