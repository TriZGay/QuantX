package io.futakotome.trade.dto.message;

public class ValuationHistoricalItem {
    private Double value; // 估值
    private Long time; // 时间戳（秒）
    private String timeStr; // 时间字符串，格式 YYYY-MM-DD，对应市场时区
    private Double plateValue; // 行业均值

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public Double getPlateValue() {
        return plateValue;
    }

    public void setPlateValue(Double plateValue) {
        this.plateValue = plateValue;
    }
}
