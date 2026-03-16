package io.futakotome.analyze.controller.vo;

public class DataInfoPerCodeResponse {
    private KLineInfoPerCode kInfo;

    public KLineInfoPerCode getkInfo() {
        return kInfo;
    }

    public void setkInfo(KLineInfoPerCode kInfo) {
        this.kInfo = kInfo;
    }

    public static class KLineInfoPerCode {
        private String maxTime;
        private String minTime;
        private String code;
        private Integer rehabType;

        public String getMaxTime() {
            return maxTime;
        }

        public void setMaxTime(String maxTime) {
            this.maxTime = maxTime;
        }

        public String getMinTime() {
            return minTime;
        }

        public void setMinTime(String minTime) {
            this.minTime = minTime;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Integer getRehabType() {
            return rehabType;
        }

        public void setRehabType(Integer rehabType) {
            this.rehabType = rehabType;
        }
    }
}
