package io.futakotome.analyze.biz.backtest;

import io.futakotome.analyze.controller.vo.BackTestOvrResponse;
import io.futakotome.analyze.controller.vo.BackTestTradeSignalResponse;

import java.util.List;
import java.util.stream.Collectors;

public class BackTestResult {
    private List<TradeSignal> signals;
    private BackTestOvr backTestOvr;

    public List<BackTestTradeSignalResponse> tradeSignalsVo() {
        return signals.stream().map(tradeSignal -> {
            BackTestTradeSignalResponse response = new BackTestTradeSignalResponse();
            response.setOpenPrice(tradeSignal.getOpen());
            response.setClosePrice(tradeSignal.getClose());
            response.setHighPrice(tradeSignal.getHigh());
            response.setLowPrice(tradeSignal.getLow());
            response.setVolume(tradeSignal.getVolume());
            response.setDatetime(tradeSignal.getDatetime());
            response.setAction(tradeSignal.getAction().toString());
            response.setPrice(tradeSignal.getPrice());
            response.setQuantity(tradeSignal.getQuantity());
            return response;
        }).collect(Collectors.toList());
    }

    public List<TradeSignal> getSignals() {
        return signals;
    }

    public void setSignals(List<TradeSignal> signals) {
        this.signals = signals;
    }

    public BackTestOvr getBackTestOvr() {
        return backTestOvr;
    }

    public void setBackTestOvr(BackTestOvr backTestOvr) {
        this.backTestOvr = backTestOvr;
    }

    public static class BackTestOvr {
        private Double finalValue;
        private Double totalProfit;
        private Double initialCapital;
        private Double commission;

        public BackTestOvrResponse toVo() {
            BackTestOvrResponse response = new BackTestOvrResponse();
            response.setFinalValue(finalValue);
            response.setTotalProfit(totalProfit);
            response.setInitialCapital(initialCapital);
            response.setCommission(commission);
            return response;
        }

        public BackTestOvr(Double finalValue, Double totalProfit, Double initialCapital, Double commission) {
            this.finalValue = finalValue;
            this.totalProfit = totalProfit;
            this.initialCapital = initialCapital;
            this.commission = commission;
        }

        public BackTestOvr() {
        }

        public Double getFinalValue() {
            return finalValue;
        }

        public void setFinalValue(Double finalValue) {
            this.finalValue = finalValue;
        }

        public Double getTotalProfit() {
            return totalProfit;
        }

        public void setTotalProfit(Double totalProfit) {
            this.totalProfit = totalProfit;
        }

        public Double getInitialCapital() {
            return initialCapital;
        }

        public void setInitialCapital(Double initialCapital) {
            this.initialCapital = initialCapital;
        }

        public Double getCommission() {
            return commission;
        }

        public void setCommission(Double commission) {
            this.commission = commission;
        }
    }
}
