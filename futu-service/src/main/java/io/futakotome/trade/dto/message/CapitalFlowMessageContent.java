package io.futakotome.trade.dto.message;

public class CapitalFlowMessageContent {
    private Integer market;
    private String code;
    private Double inFlow;
    private String time;
    private Double timestamp;
    private Double mainInFlow;
    private Double superInFlow;
    private Double bigInFlow;
    private Double midInFlow;
    private Double smlInFlow;
    private String lastValidTime;

    public String getLastValidTime() {
        return lastValidTime;
    }

    public void setLastValidTime(String lastValidTime) {
        this.lastValidTime = lastValidTime;
    }

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
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
}
