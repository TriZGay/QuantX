package io.futakotome.common.message;

public class RTBrokerMessage {
    private Integer market;
    private String code;
    private Long brokerId;
    private String brokerName;
    private Integer brokerPos;
    private Integer askOrBid;
    private Long orderId;
    private Long volume;

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

    public Long getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Long brokerId) {
        this.brokerId = brokerId;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public Integer getBrokerPos() {
        return brokerPos;
    }

    public void setBrokerPos(Integer brokerPos) {
        this.brokerPos = brokerPos;
    }

    public Integer getAskOrBid() {
        return askOrBid;
    }

    public void setAskOrBid(Integer askOrBid) {
        this.askOrBid = askOrBid;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }
}
