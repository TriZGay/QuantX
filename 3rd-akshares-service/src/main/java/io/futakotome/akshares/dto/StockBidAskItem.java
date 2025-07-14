package io.futakotome.akshares.dto;

public class StockBidAskItem {
    private String item;
    private Double value;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "StockBidAskItem{" +
                "item='" + item + '\'' +
                ", value=" + value +
                '}';
    }
}
