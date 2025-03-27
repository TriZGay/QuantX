package io.futakotome.analyze.biz.backtest;

public class TradeSignal {
    private String datetime;
    private Action action;
    private Double price;
    private Integer quantity;

    public TradeSignal() {
    }

    public TradeSignal(String datetime, Action action, Double price, Integer quantity) {
        this.datetime = datetime;
        this.action = action;
        this.price = price;
        this.quantity = quantity;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public enum Action {
        BUY, SELL
    }
}
