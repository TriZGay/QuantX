package io.futakotome.common.message;

import java.util.List;

public class HistoryKLDetailMessage {
    private Integer usedQuota;
    private Integer remainQuota;
    private List<HistoryKLDetailItemMessage> detailList;

    public Integer getUsedQuota() {
        return usedQuota;
    }

    public void setUsedQuota(Integer usedQuota) {
        this.usedQuota = usedQuota;
    }

    public Integer getRemainQuota() {
        return remainQuota;
    }

    public void setRemainQuota(Integer remainQuota) {
        this.remainQuota = remainQuota;
    }

    public List<HistoryKLDetailItemMessage> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<HistoryKLDetailItemMessage> detailList) {
        this.detailList = detailList;
    }

    public static class HistoryKLDetailItemMessage {
        private Integer market;
        private String code;
        private String name;
        private String requestTime;
        private Long requestTimeStamp;

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

        public String getRequestTime() {
            return requestTime;
        }

        public void setRequestTime(String requestTime) {
            this.requestTime = requestTime;
        }

        public Long getRequestTimeStamp() {
            return requestTimeStamp;
        }

        public void setRequestTimeStamp(Long requestTimeStamp) {
            this.requestTimeStamp = requestTimeStamp;
        }
    }
}
