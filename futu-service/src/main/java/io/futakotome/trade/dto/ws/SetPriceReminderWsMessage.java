package io.futakotome.trade.dto.ws;

public class SetPriceReminderWsMessage implements Message {
    private Integer market;
    private String code;
    private Integer op;
    private Long key;
    private Integer remindType;
    private Integer remindFreq;
    private Double value;
    private String note;

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

    public Integer getOp() {
        return op;
    }

    public void setOp(Integer op) {
        this.op = op;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public Integer getRemindType() {
        return remindType;
    }

    public void setRemindType(Integer remindType) {
        this.remindType = remindType;
    }

    public Integer getRemindFreq() {
        return remindFreq;
    }

    public void setRemindFreq(Integer remindFreq) {
        this.remindFreq = remindFreq;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public MessageType getType() {
        return MessageType.SET_PRICE_REMINDER;
    }
}
