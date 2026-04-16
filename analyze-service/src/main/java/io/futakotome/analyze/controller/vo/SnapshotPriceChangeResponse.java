package io.futakotome.analyze.controller.vo;

import java.util.List;

public class SnapshotPriceChangeResponse {
    private List<PriceChangeResponse> raises;
    private List<PriceChangeResponse> reduces;

    public List<PriceChangeResponse> getRaises() {
        return raises;
    }

    public void setRaises(List<PriceChangeResponse> raises) {
        this.raises = raises;
    }

    public List<PriceChangeResponse> getReduces() {
        return reduces;
    }

    public void setReduces(List<PriceChangeResponse> reduces) {
        this.reduces = reduces;
    }

    public static class PriceChangeResponse{
        private Integer market;
        private String code;
        private String name;
        private String updateTime;
        private Double priceChange;

        public Integer getMarket() {
            return market;
        }

        public void setMarket(Integer market) {
            this.market = market;
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

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public Double getPriceChange() {
            return priceChange;
        }

        public void setPriceChange(Double priceChange) {
            this.priceChange = priceChange;
        }
    }
}
