package io.futakotome.trade.dto.message;

public class MacroIndicatorInfo {
    private Long indicatorId; //宏观指标ID
    private String name;        //指标名称

    public Long getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(Long indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
