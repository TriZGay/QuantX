package io.futakotome.akshares.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockShSummary {
    @JsonProperty(value = "项目")
    private String name;
    @JsonProperty("股票")
    private Double stock;
    @JsonProperty("主板")
    private Double main;
    @JsonProperty("科创板")
    private Double sen;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public Double getMain() {
        return main;
    }

    public void setMain(Double main) {
        this.main = main;
    }

    public Double getSen() {
        return sen;
    }

    public void setSen(Double sen) {
        this.sen = sen;
    }

    @Override
    public String toString() {
        return "SHStockSummary{" +
                "name='" + name + '\'' +
                ", stock=" + stock +
                ", main=" + main +
                ", sen=" + sen +
                '}';
    }
}
