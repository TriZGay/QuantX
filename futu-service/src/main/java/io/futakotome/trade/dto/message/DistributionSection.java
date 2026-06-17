package io.futakotome.trade.dto.message;

public class DistributionSection {
    private Double start; // 区间开始值
    private Double end; // 区间结束值（0 表示无上限）
    private Integer number; // 该区间个股数量

    public Double getStart() {
        return start;
    }

    public void setStart(Double start) {
        this.start = start;
    }

    public Double getEnd() {
        return end;
    }

    public void setEnd(Double end) {
        this.end = end;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
