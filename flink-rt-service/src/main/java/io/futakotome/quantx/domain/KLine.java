package io.futakotome.quantx.domain;

import org.apache.flink.api.java.functions.KeySelector;

public class KLine {
    public static class CodeRehabTypeKeySelector implements KeySelector<KLineDto, Keys.CodeRehabTypeKey> {
        @Override
        public Keys.CodeRehabTypeKey getKey(KLineDto kLineDto) throws Exception {
            return new Keys.CodeRehabTypeKey(kLineDto.getCode(), kLineDto.getRehabType());
        }
    }

    public static class KLineDto {
        private Integer market;
        private String code;
        private Integer rehabType;
        private Double highPrice;
        private Double openPrice;
        private Double lowPrice;
        private Double closePrice;
        private Double lastClosePrice;
        private Long volume;
        private Double turnover;
        private Double turnoverRate;
        private Double pe;
        private Double changeRate;
        private String updateTime;
        private String addTime;

        public KLineDto() {
        }

        public void setMarket(Integer market) {
            this.market = market;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setRehabType(Integer rehabType) {
            this.rehabType = rehabType;
        }

        public void setHighPrice(Double highPrice) {
            this.highPrice = highPrice;
        }

        public void setOpenPrice(Double openPrice) {
            this.openPrice = openPrice;
        }

        public void setLowPrice(Double lowPrice) {
            this.lowPrice = lowPrice;
        }

        public void setClosePrice(Double closePrice) {
            this.closePrice = closePrice;
        }

        public void setLastClosePrice(Double lastClosePrice) {
            this.lastClosePrice = lastClosePrice;
        }

        public void setVolume(Long volume) {
            this.volume = volume;
        }

        public void setTurnover(Double turnover) {
            this.turnover = turnover;
        }

        public void setTurnoverRate(Double turnoverRate) {
            this.turnoverRate = turnoverRate;
        }

        public void setPe(Double pe) {
            this.pe = pe;
        }

        public void setChangeRate(Double changeRate) {
            this.changeRate = changeRate;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public KLineDto(Integer market, String code, Integer rehabType, Double highPrice, Double openPrice, Double lowPrice, Double closePrice, Double lastClosePrice, Long volume, Double turnover, Double turnoverRate, Double pe, Double changeRate, String updateTime, String addTime) {
            this.market = market;
            this.code = code;
            this.rehabType = rehabType;
            this.highPrice = highPrice;
            this.openPrice = openPrice;
            this.lowPrice = lowPrice;
            this.closePrice = closePrice;
            this.lastClosePrice = lastClosePrice;
            this.volume = volume;
            this.turnover = turnover;
            this.turnoverRate = turnoverRate;
            this.pe = pe;
            this.changeRate = changeRate;
            this.updateTime = updateTime;
            this.addTime = addTime;
        }

        public Integer getMarket() {
            return market;
        }

        public String getCode() {
            return code;
        }

        public Integer getRehabType() {
            return rehabType;
        }

        public Double getHighPrice() {
            return highPrice;
        }

        public Double getOpenPrice() {
            return openPrice;
        }

        public Double getLowPrice() {
            return lowPrice;
        }

        public Double getClosePrice() {
            return closePrice;
        }

        public Double getLastClosePrice() {
            return lastClosePrice;
        }

        public Long getVolume() {
            return volume;
        }

        public Double getTurnover() {
            return turnover;
        }

        public Double getTurnoverRate() {
            return turnoverRate;
        }

        public Double getPe() {
            return pe;
        }

        public Double getChangeRate() {
            return changeRate;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public String getAddTime() {
            return addTime;
        }
    }
}
