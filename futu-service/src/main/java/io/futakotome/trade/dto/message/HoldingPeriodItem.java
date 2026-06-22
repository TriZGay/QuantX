package io.futakotome.trade.dto.message;

public class HoldingPeriodItem {
    private String periodText; // 报告期，如 "2025/Q3"
    private Integer periodId; // 报告期 ID，下次请求原样传入

    public String getPeriodText() {
        return periodText;
    }

    public void setPeriodText(String periodText) {
        this.periodText = periodText;
    }

    public Integer getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Integer periodId) {
        this.periodId = periodId;
    }
}
