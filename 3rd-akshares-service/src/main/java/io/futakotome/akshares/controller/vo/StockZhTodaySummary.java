package io.futakotome.akshares.controller.vo;

import io.futakotome.akshares.dto.StockShSummary;
import io.futakotome.akshares.dto.StockSzSummary;

import java.util.List;
import java.util.stream.Collectors;

public class StockZhTodaySummary {
    private List<StockShSummaryVo> shStockSummaries;
    private List<StockSzSummaryVo> szSummaries;

    public List<StockShSummaryVo> getShStockSummaries() {
        return shStockSummaries;
    }

    public void setShStockSummaries(List<StockShSummaryVo> shStockSummaries) {
        this.shStockSummaries = shStockSummaries;
    }

    public List<StockSzSummaryVo> getSzSummaries() {
        return szSummaries;
    }

    public void setSzSummaries(List<StockSzSummaryVo> szSummaries) {
        this.szSummaries = szSummaries;
    }

    public static class StockShSummaryVo {
        private String name;
        private Double stock;
        private Double main;
        private Double sen;

        public static List<StockShSummaryVo> dtoListToVoList(List<StockShSummary> dtos) {
            return dtos.stream().map(StockShSummaryVo::dtoToVo)
                    .collect(Collectors.toList());
        }

        private static StockShSummaryVo dtoToVo(StockShSummary dto) {
            StockShSummaryVo vo = new StockShSummaryVo();
            vo.setName(dto.getName());
            vo.setStock(dto.getStock());
            vo.setMain(dto.getMain());
            vo.setSen(dto.getSen());
            return vo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getStock() {
            return stock;
        }

        public void setStock(Double stock) {
            this.stock = stock;
        }

        public Double getMain() {
            return main;
        }

        public void setMain(Double main) {
            this.main = main;
        }

        public Double getSen() {
            return sen;
        }

        public void setSen(Double sen) {
            this.sen = sen;
        }
    }

    public static class StockSzSummaryVo {
        private String type;
        private Long num;
        private Double turnoverVal;
        private Double totalMarketVal;
        private Double circularMarketVal;

        public static List<StockSzSummaryVo> dtoListToVoList(List<StockSzSummary> dtos) {
            return dtos.stream().map(StockSzSummaryVo::dtoToVo)
                    .collect(Collectors.toList());
        }

        private static StockSzSummaryVo dtoToVo(StockSzSummary dto) {
            StockSzSummaryVo vo = new StockSzSummaryVo();
            vo.setType(dto.getType());
            vo.setNum(dto.getNum());
            vo.setTurnoverVal(dto.getTurnoverVal());
            vo.setTotalMarketVal( dto.getTotalMarketVal());
            vo.setCircularMarketVal(dto.getCircularMarketVal());
            return vo;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Long getNum() {
            return num;
        }

        public void setNum(Long num) {
            this.num = num;
        }

        public Double getTurnoverVal() {
            return turnoverVal;
        }

        public void setTurnoverVal(Double turnoverVal) {
            this.turnoverVal = turnoverVal;
        }

        public Double getTotalMarketVal() {
            return totalMarketVal;
        }

        public void setTotalMarketVal(Double totalMarketVal) {
            this.totalMarketVal = totalMarketVal;
        }

        public Double getCircularMarketVal() {
            return circularMarketVal;
        }

        public void setCircularMarketVal(Double circularMarketVal) {
            this.circularMarketVal = circularMarketVal;
        }
    }
}
