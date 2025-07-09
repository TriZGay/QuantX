package io.futakotome.akshares.dto;

public class StockItem {
    private String item;
    private String value;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "StockItem{" +
                "item='" + item + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
