package io.futakotome.akshares.controller.vo;

import io.futakotome.akshares.dto.StockUsRTPrice;

import java.util.List;
import java.util.stream.Collectors;

public class StockUsRtResponse {
    private List<StockUsRtPriceVo> stockUsRtPrices;

    public List<StockUsRtPriceVo> getStockUsRtPrices() {
        return stockUsRtPrices;
    }

    public void setStockUsRtPrices(List<StockUsRtPriceVo> stockUsRtPrices) {
        this.stockUsRtPrices = stockUsRtPrices;
    }

    public static class StockUsRtPriceVo {
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
        private Double turnoverRatio;
        private Double marketCap;
        private Double peRatio;

        public static List<StockUsRtPriceVo> dtoListToVoList(List<StockUsRTPrice> dtos) {
            return dtos.stream().map(StockUsRtPriceVo::dtoToVo).collect(Collectors.toList());
        }

        private static StockUsRtPriceVo dtoToVo(StockUsRTPrice dto) {
            StockUsRtPriceVo stockUsRtPriceVo = new StockUsRtPriceVo();
            stockUsRtPriceVo.setId(dto.getId());
            stockUsRtPriceVo.setCode(dto.getCode());
            stockUsRtPriceVo.setName(dto.getName());
            stockUsRtPriceVo.setPrice(dto.getPrice());
            stockUsRtPriceVo.setRatio(dto.getRatio());
            stockUsRtPriceVo.setRatioVal(dto.getRatioVal());
            stockUsRtPriceVo.setTurnover(dto.getTurnover());
            stockUsRtPriceVo.setVolume(dto.getVolume());
            stockUsRtPriceVo.setAmplitude(dto.getAmplitude());
            stockUsRtPriceVo.setHigh(dto.getHigh());
            stockUsRtPriceVo.setLow(dto.getLow());
            stockUsRtPriceVo.setOpen(dto.getOpen());
            stockUsRtPriceVo.setClose(dto.getClose());
            stockUsRtPriceVo.setTurnoverRatio(dto.getTurnoverRatio());
            stockUsRtPriceVo.setMarketCap(dto.getMarketCap());
            stockUsRtPriceVo.setPeRatio(dto.getPeRatio());
            return stockUsRtPriceVo;
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

        public Double getTurnoverRatio() {
            return turnoverRatio;
        }

        public void setTurnoverRatio(Double turnoverRatio) {
            this.turnoverRatio = turnoverRatio;
        }

        public Double getMarketCap() {
            return marketCap;
        }

        public void setMarketCap(Double marketCap) {
            this.marketCap = marketCap;
        }

        public Double getPeRatio() {
            return peRatio;
        }

        public void setPeRatio(Double peRatio) {
            this.peRatio = peRatio;
        }
    }
}
