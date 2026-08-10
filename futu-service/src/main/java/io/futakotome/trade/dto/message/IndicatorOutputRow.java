package io.futakotome.trade.dto.message;

import java.util.List;

public class IndicatorOutputRow {
    private String time;    //K 线时间（与请求中的 KLine.time 一致）
    private List<Double> values;  //各输出线在该时刻的值

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }
}
