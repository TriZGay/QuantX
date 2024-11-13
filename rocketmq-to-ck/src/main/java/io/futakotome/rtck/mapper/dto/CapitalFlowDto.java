package io.futakotome.rtck.mapper.dto;

public class CapitalFlowDto {
    private Integer market;
    private String code;
    private Double inFlow; //整体净流入
    private Double mainInFlow; //主力大单净流入,//仅周期类型不为'实时'有效
    private Double superInFlow; //特大单净流入
    private Double bigInFlow; //大单净流入
    private Double midInFlow; //中单净流入
    private Double smlInFlow; //小单净流入
    private String time; //开始时间,分钟为单位
    private String lastValidTime;

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getInFlow() {
        return inFlow;
    }

    public void setInFlow(Double inFlow) {
        this.inFlow = inFlow;
    }

    public Double getMainInFlow() {
        return mainInFlow;
    }

    public void setMainInFlow(Double mainInFlow) {
        this.mainInFlow = mainInFlow;
    }

    public Double getSuperInFlow() {
        return superInFlow;
    }

    public void setSuperInFlow(Double superInFlow) {
        this.superInFlow = superInFlow;
    }

    public Double getBigInFlow() {
        return bigInFlow;
    }

    public void setBigInFlow(Double bigInFlow) {
        this.bigInFlow = bigInFlow;
    }

    public Double getMidInFlow() {
        return midInFlow;
    }

    public void setMidInFlow(Double midInFlow) {
        this.midInFlow = midInFlow;
    }

    public Double getSmlInFlow() {
        return smlInFlow;
    }

    public void setSmlInFlow(Double smlInFlow) {
        this.smlInFlow = smlInFlow;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLastValidTime() {
        return lastValidTime;
    }

    public void setLastValidTime(String lastValidTime) {
        this.lastValidTime = lastValidTime;
    }

    @Override
    public String toString() {
        return "CapitalFlowDto{" +
                "market=" + market +
                ", code='" + code + '\'' +
                ", inFlow=" + inFlow +
                ", mainInFlow=" + mainInFlow +
                ", superInFlow=" + superInFlow +
                ", bigInFlow=" + bigInFlow +
                ", midInFlow=" + midInFlow +
                ", smlInFlow=" + smlInFlow +
                ", time='" + time + '\'' +
                ", lastValidTime='" + lastValidTime + '\'' +
                '}';
    }
}
