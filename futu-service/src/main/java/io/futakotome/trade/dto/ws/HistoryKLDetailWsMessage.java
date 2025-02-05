package io.futakotome.trade.dto.ws;

import java.util.List;

public class HistoryKLDetailWsMessage implements Message {
    private Integer usedQuota;
    private Integer remainQuota;
    private List<HistoryKLDetailItemWsMessage> itemList;

    @Override
    public String toString() {
        return "HistoryKLDetailWsMessage{" +
                "usedQuota=" + usedQuota +
                ", remainQuota=" + remainQuota +
                ", itemList=" + itemList +
                '}';
    }

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

    public List<HistoryKLDetailItemWsMessage> getItemList() {
        return itemList;
    }

    public void setItemList(List<HistoryKLDetailItemWsMessage> itemList) {
        this.itemList = itemList;
    }

    @Override
    public MessageType getType() {
        return MessageType.KL_HISTORY_DETAIL;
    }

    public static class HistoryKLDetailItemWsMessage {
        private Integer market;
        private String code;
        private String name;
        private String requestTime;
        private Long requestTimeStamp;

        @Override
        public String toString() {
            return "HistoryKLDetailItemWsMessage{" +
                    "market=" + market +
                    ", code='" + code + '\'' +
                    ", name='" + name + '\'' +
                    ", requestTime='" + requestTime + '\'' +
                    ", requestTimeStamp=" + requestTimeStamp +
                    '}';
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getRequestTime() {
            return requestTime;
        }

        public void setRequestTime(String requestTime) {
            this.requestTime = requestTime;
        }

        public Integer getMarket() {
            return market;
        }

        public void setMarket(Integer market) {
            this.market = market;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getRequestTimeStamp() {
            return requestTimeStamp;
        }

        public void setRequestTimeStamp(Long requestTimeStamp) {
            this.requestTimeStamp = requestTimeStamp;
        }
    }
}
