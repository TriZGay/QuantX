package io.futakotome.akshares.controller.vo;

import io.futakotome.akshares.dto.StockRTPrice;

import java.util.List;
import java.util.stream.Collectors;

public class StockZhRtResponse {
    private List<StockRTPriceVo> rtPrices;

    public List<StockRTPriceVo> getRtPrices() {
        return rtPrices;
    }

    public void setRtPrices(List<StockRTPriceVo> rtPrices) {
        this.rtPrices = rtPrices;
    }

    public static class StockRTPriceVo {
        private Long id;
        private String code;
        private String name;
        private Double price;
        private Double ratio;
        private Double ratioVal;
        private Double turnover;
        private Double volume;
        private Double amplitude;
        private Double high;
        private Double low;
        private Double open;
        private Double close;
        private Double equivalentRatio;
        private Double turnoverRatio;
        private Double peRatio;
        private Double pbRatio;
        private Double marketCap;
        private Double circularRatio;
        private Double growthRatio;
        private Double fiveMRatio;
        private Double sixtyDRatio;
        private Double ytdPercentRatio;

        public static List<StockRTPriceVo> dtoListToVoList(List<StockRTPrice> stockRTPrices) {
            return stockRTPrices.stream().map(StockRTPriceVo::dtoToVo)
                    .collect(Collectors.toList());
        }

        private static StockRTPriceVo dtoToVo(StockRTPrice dto) {
            StockRTPriceVo stockRTPriceVo = new StockRTPriceVo();
            stockRTPriceVo.setId(dto.getId());
            stockRTPriceVo.setCode(dto.getCode());
            stockRTPriceVo.setName(dto.getName());
            stockRTPriceVo.setPrice(dto.getPrice());
            stockRTPriceVo.setRatio(dto.getRatio());
            stockRTPriceVo.setRatioVal(dto.getRatioVal());
            stockRTPriceVo.setTurnover(dto.getTurnover());
            stockRTPriceVo.setVolume(dto.getVolume());
            stockRTPriceVo.setAmplitude(dto.getAmplitude());
            stockRTPriceVo.setHigh(dto.getHigh());
            stockRTPriceVo.setLow(dto.getLow());
            stockRTPriceVo.setOpen(dto.getOpen());
            stockRTPriceVo.setClose(dto.getClose());
            stockRTPriceVo.setEquivalentRatio(dto.getEquivalentRatio());
            stockRTPriceVo.setTurnoverRatio(dto.getTurnoverRatio());
            stockRTPriceVo.setPeRatio(dto.getPeRatio());
            stockRTPriceVo.setPbRatio(dto.getPbRatio());
            stockRTPriceVo.setMarketCap(dto.getMarketCap());
            stockRTPriceVo.setCircularRatio(dto.getCircularRatio());
            stockRTPriceVo.setGrowthRatio(dto.getGrowthRatio());
            stockRTPriceVo.setFiveMRatio(dto.getFiveMRatio());
            stockRTPriceVo.setSixtyDRatio(dto.getSixtyDRatio());
            stockRTPriceVo.setYtdPercentRatio(dto.getYtdPercentRatio());
            return stockRTPriceVo;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Double getRatio() {
            return ratio;
        }

        public void setRatio(Double ratio) {
            this.ratio = ratio;
        }

        public Double getRatioVal() {
            return ratioVal;
        }

        public void setRatioVal(Double ratioVal) {
            this.ratioVal = ratioVal;
        }

        public Double getTurnover() {
            return turnover;
        }

        public void setTurnover(Double turnover) {
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

        public Double getEquivalentRatio() {
            return equivalentRatio;
        }

        public void setEquivalentRatio(Double equivalentRatio) {
            this.equivalentRatio = equivalentRatio;
        }

        public Double getTurnoverRatio() {
            return turnoverRatio;
        }

        public void setTurnoverRatio(Double turnoverRatio) {
            this.turnoverRatio = turnoverRatio;
        }

        public Double getPeRatio() {
            return peRatio;
        }

        public void setPeRatio(Double peRatio) {
            this.peRatio = peRatio;
        }

        public Double getPbRatio() {
            return pbRatio;
        }

        public void setPbRatio(Double pbRatio) {
            this.pbRatio = pbRatio;
        }

        public Double getMarketCap() {
            return marketCap;
        }

        public void setMarketCap(Double marketCap) {
            this.marketCap = marketCap;
        }

        public Double getCircularRatio() {
            return circularRatio;
        }

        public void setCircularRatio(Double circularRatio) {
            this.circularRatio = circularRatio;
        }

        public Double getGrowthRatio() {
            return growthRatio;
        }

        public void setGrowthRatio(Double growthRatio) {
            this.growthRatio = growthRatio;
        }

        public Double getFiveMRatio() {
            return fiveMRatio;
        }

        public void setFiveMRatio(Double fiveMRatio) {
            this.fiveMRatio = fiveMRatio;
        }

        public Double getSixtyDRatio() {
            return sixtyDRatio;
        }

        public void setSixtyDRatio(Double sixtyDRatio) {
            this.sixtyDRatio = sixtyDRatio;
        }

        public Double getYtdPercentRatio() {
            return ytdPercentRatio;
        }

        public void setYtdPercentRatio(Double ytdPercentRatio) {
            this.ytdPercentRatio = ytdPercentRatio;
        }
    }
}
