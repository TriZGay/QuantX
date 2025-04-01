package io.futakotome.analyze.controller.vo;

import java.util.List;

public class BackTestResponse {
    List<KLineResponse> prices;
    List<BackTestTradeSignalResponse> tradeSignals;
    BackTestOvrResponse backTestOvr;

    public List<KLineResponse> getPrices() {
        return prices;
    }

    public void setPrices(List<KLineResponse> prices) {
        this.prices = prices;
    }

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
