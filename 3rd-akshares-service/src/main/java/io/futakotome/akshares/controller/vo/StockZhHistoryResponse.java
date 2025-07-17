package io.futakotome.akshares.controller.vo;

import io.futakotome.akshares.dto.StockZhHistory;

import java.util.List;
import java.util.stream.Collectors;

public class StockZhHistoryResponse {
    private List<StockZhHistoryVo> histories;

    public List<StockZhHistoryVo> getHistories() {
        return histories;
    }

    public void setHistories(List<StockZhHistoryVo> histories) {
        this.histories = histories;
    }

    public static class StockZhHistoryVo {
        private String date;
        private String code;
        private Double open;
        private Double close;
        private Double high;
        private Double low;
        private Long turnover;
        private Double volume;
        private Double amplitude;
        private Double chg;
        private Double change;
        private Double turnoverRatio;

        public static List<StockZhHistoryVo> dtoListToVoList(List<StockZhHistory> stockZhHistories) {
            return stockZhHistories.stream()
                    .map(StockZhHistoryVo::dtoToVo)
                    .collect(Collectors.toList());
        }

        private static StockZhHistoryVo dtoToVo(StockZhHistory stockZhHistory) {
            StockZhHistoryVo stockZhHistoryVo = new StockZhHistoryVo();
            stockZhHistoryVo.setDate(stockZhHistory.getDate().split("T")[0]);
            stockZhHistoryVo.setCode(stockZhHistory.getCode());
            stockZhHistoryVo.setOpen(stockZhHistory.getOpen());
            stockZhHistoryVo.setClose(stockZhHistory.getClose());
            stockZhHistoryVo.setHigh(stockZhHistory.getHigh());
            stockZhHistoryVo.setLow(stockZhHistory.getLow());
            stockZhHistoryVo.setTurnover(stockZhHistory.getTurnover());
            stockZhHistoryVo.setVolume(stockZhHistory.getVolume());
            stockZhHistoryVo.setAmplitude(stockZhHistory.getAmplitude());
            stockZhHistoryVo.setChg(stockZhHistory.getChg());
            stockZhHistoryVo.setChange(stockZhHistory.getChange());
            stockZhHistoryVo.setTurnoverRatio(stockZhHistory.getTurnoverRatio());
            return stockZhHistoryVo;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Double getOpen() {
            return open;
        }

        public void setOpen(Double open) {
            this.open = open;
        }

        public Double getClose() {
            return close;
        }

        public void setClose(Double close) {
            this.close = close;
        }

        public Double getHigh() {
            return high;
        }

        public void setHigh(Double high) {
            this.high = high;
        }

        public Double getLow() {
            return low;
        }

        public void setLow(Double low) {
            this.low = low;
        }

        public Long getTurnover() {
            return turnover;
        }

        public void setTurnover(Long turnover) {
            this.turnover = turnover;
        }

        public Double getVolume() {
            return volume;
        }

        public void setVolume(Double volume) {
            this.volume = volume;
        }

        public Double getAmplitude() {
            return amplitude;
        }

        public void setAmplitude(Double amplitude) {
            this.amplitude = amplitude;
        }

        public Double getChg() {
            return chg;
        }

        public void setChg(Double chg) {
            this.chg = chg;
        }

        public Double getChange() {
            return change;
        }

        public void setChange(Double change) {
            this.change = change;
        }

        public Double getTurnoverRatio() {
            return turnoverRatio;
        }

        public void setTurnoverRatio(Double turnoverRatio) {
            this.turnoverRatio = turnoverRatio;
        }
    }
}
