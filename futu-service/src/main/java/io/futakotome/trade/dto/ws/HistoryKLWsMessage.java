package io.futakotome.trade.dto.ws;

public class HistoryKLWsMessage implements Message {
    private Integer market;
    private String code;
    private Integer klType;
    private String beginDate;
    private String endDate;

    public HistoryKLWsMessage() {
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

    public Integer getKlType() {
        return klType;
    }

    public void setKlType(Integer klType) {
        this.klType = klType;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public MessageType getType() {
        return MessageType.KL_HISTORY;
    }
}
