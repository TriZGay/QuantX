package io.futakotome.analyze.biz.backtest;

import io.futakotome.analyze.controller.vo.BackTestTradeSignalResponse;

public class TradeSignal {
    private String datetime;
    private Action action;
    private Double price;
    private Integer quantity;

    private Double open;
    private Double close;
    private Double high;
    private Double low;
    private Long volume;

    public TradeSignal() {
    }

    public TradeSignal(String datetime, Action action, Double price, Integer quantity,
                       Double open, Double close, Double high, Double low, Long volume) {
        this.datetime = datetime;
        this.action = action;
        this.price = price;
        this.quantity = quantity;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.volume = volume;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
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
        BUY, SELL, HOLD
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
