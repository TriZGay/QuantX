package io.futakotome.quantx.pojo;

public enum TradeDirection {
    BUY(1),
    SELL(2);

    private final Integer direction;

    TradeDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getDirection() {
        return direction;
    }
}
