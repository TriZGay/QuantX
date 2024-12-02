package io.futakotome.analyze.controller.vo;

public class TradeDateRange {
    private String amStart;
    private String amEnd;
    private String pmStart;
    private String pmEnd;

    public String getAmStart() {
        return amStart;
    }

    public void setAmStart(String amStart) {
        this.amStart = amStart;
    }

    public String getAmEnd() {
        return amEnd;
    }

    public void setAmEnd(String amEnd) {
        this.amEnd = amEnd;
    }

    public String getPmStart() {
        return pmStart;
    }

    public void setPmStart(String pmStart) {
        this.pmStart = pmStart;
    }

    public String getPmEnd() {
        return pmEnd;
    }

    public void setPmEnd(String pmEnd) {
        this.pmEnd = pmEnd;
    }
}
