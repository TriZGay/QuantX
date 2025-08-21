package io.futakotome.analyze.controller.vo;

import java.util.List;

public class BackTestResponse {
    private List<BackTestTradeSignalResponse> tradeSignals;
    private BackTestOvrResponse backTestOvr;

    public List<BackTestTradeSignalResponse> getTradeSignals() {
        return tradeSignals;
    }

    public void setTradeSignals(List<BackTestTradeSignalResponse> tradeSignals) {
        this.tradeSignals = tradeSignals;
    }

    public BackTestOvrResponse getBackTestOvr() {
        return backTestOvr;
    }

    public void setBackTestOvr(BackTestOvrResponse backTestOvr) {
        this.backTestOvr = backTestOvr;
    }
}
