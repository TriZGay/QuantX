package io.futakotome.trade.controller.vo;

public class AntDesignSelectOptions {
    private String label;
    private Integer value;

    public AntDesignSelectOptions(String label, Integer value) {
        this.label = label;
        this.value = value;
    }

    public AntDesignSelectOptions() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
