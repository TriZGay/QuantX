package io.futakotome.trade.dto.message;

public class CapitalFlowMessageContent {
    private Double inFlow;
    private Double mainInFlow;
    private Double superInFlow;
    private Double bigInFlow;
    private Double midInFlow;
    private Double smlInFlow;
    private String time;

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
}
