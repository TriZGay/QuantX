package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.IndicatorCalcResult;

import java.util.List;

public class IndicatorCalcWsMessage implements Message {
    private String shortName;
    private Integer langType;
    private IndicatorCalcData data;
    private Integer num;
    private List<IndicatorCalcInput> inputs;
    private IndicatorCalcResult content;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getLangType() {
        return langType;
    }

    public void setLangType(Integer langType) {
        this.langType = langType;
    }

    public IndicatorCalcData getData() {
        return data;
    }

    public void setData(IndicatorCalcData data) {
        this.data = data;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public List<IndicatorCalcInput> getInputs() {
        return inputs;
    }

    public void setInputs(List<IndicatorCalcInput> inputs) {
        this.inputs = inputs;
    }

    public IndicatorCalcResult getContent() {
        return content;
    }

    public void setContent(IndicatorCalcResult content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.INDICATOR_CALC;
    }
}
