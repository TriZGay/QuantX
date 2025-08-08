package io.futakotome.quantx.domain;

import org.apache.flink.api.java.functions.KeySelector;

public class Ema {

    public static class CodeRehabTypeKeySelector implements KeySelector<EmaDto, Keys.CodeRehabTypeKey> {
        @Override
        public Keys.CodeRehabTypeKey getKey(EmaDto emaDto) throws Exception {
            return new Keys.CodeRehabTypeKey(emaDto.getCode(), emaDto.getRehabType());
        }
    }

    public static class EmaDto {
        private Integer market;
        private String code;
        private Integer rehabType;
        private Double ema_5;
        private Double ema_10;
        private Double ema_12;
        private Double ema_20;
        private Double ema_26;
        private Double ema_60;
        private Double ema_120;
        private String update_time;

        public EmaDto() {
        }

        public EmaDto(Integer market, String code, Integer rehabType, Double ema_5, Double ema_10, Double ema_12, Double ema_20, Double ema_26, Double ema_60, Double ema_120, String update_time) {
            this.market = market;
            this.code = code;
            this.rehabType = rehabType;
            this.ema_5 = ema_5;
            this.ema_10 = ema_10;
            this.ema_12 = ema_12;
            this.ema_20 = ema_20;
            this.ema_26 = ema_26;
            this.ema_60 = ema_60;
            this.ema_120 = ema_120;
            this.update_time = update_time;
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

        public void setEma_5(Double ema_5) {
            this.ema_5 = ema_5;
        }

        public void setEma_10(Double ema_10) {
            this.ema_10 = ema_10;
        }

        public void setEma_12(Double ema_12) {
            this.ema_12 = ema_12;
        }

        public void setEma_20(Double ema_20) {
            this.ema_20 = ema_20;
        }

        public void setEma_26(Double ema_26) {
            this.ema_26 = ema_26;
        }

        public void setEma_60(Double ema_60) {
            this.ema_60 = ema_60;
        }

        public void setEma_120(Double ema_120) {
            this.ema_120 = ema_120;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
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

        public Double getEma_5() {
            return ema_5;
        }

        public Double getEma_10() {
            return ema_10;
        }

        public Double getEma_12() {
            return ema_12;
        }

        public Double getEma_20() {
            return ema_20;
        }

        public Double getEma_26() {
            return ema_26;
        }

        public Double getEma_60() {
            return ema_60;
        }

        public Double getEma_120() {
            return ema_120;
        }

        public String getUpdate_time() {
            return update_time;
        }

        @Override
        public String toString() {
            return "EmaDto{" +
                    "market=" + market +
                    ", code='" + code + '\'' +
                    ", rehabType=" + rehabType +
                    ", ema_5=" + ema_5 +
                    ", ema_10=" + ema_10 +
                    ", ema_12=" + ema_12 +
                    ", ema_20=" + ema_20 +
                    ", ema_26=" + ema_26 +
                    ", ema_60=" + ema_60 +
                    ", ema_120=" + ema_120 +
                    ", update_time='" + update_time + '\'' +
                    '}';
        }
    }
}
