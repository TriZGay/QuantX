package io.futakotome.analyze.biz.backtest;

import io.futakotome.analyze.controller.vo.BackTestTradeSignalResponse;

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
        BUY, SELL, NONE
    }

    public static BackTestTradeSignalResponse dto2Vo(TradeSignal dto) {
        BackTestTradeSignalResponse response = new BackTestTradeSignalResponse();
        response.setDatetime(dto.getDatetime());
        response.setAction(dto.getAction().toString());
        response.setPrice(dto.getPrice());
        response.setQuantity(dto.getQuantity());
        return response;
    }

}
