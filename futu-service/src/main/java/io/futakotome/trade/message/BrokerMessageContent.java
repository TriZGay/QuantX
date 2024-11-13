package io.futakotome.trade.message;

public class BrokerMessageContent {
    private Integer market;
    private String code;

    private Long id;
    private String name;
    private Integer pos;
    private Long orderID;
    private Long volume;
    private Integer askOrBid;

    public Integer getAskOrBid() {
        return askOrBid;
    }

    public void setAskOrBid(Integer askOrBid) {
        this.askOrBid = askOrBid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

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
}
