package io.futakotome.trade.dto.message;

import java.util.List;

public class IndicatorInfo {
    private String shortName;
    private String fullName;
    private List<IndicatorIOParam> inputs;
    private List<IndicatorIOParam> outputs;
    private String script;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<IndicatorIOParam> getInputs() {
        return inputs;
    }

    public void setInputs(List<IndicatorIOParam> inputs) {
        this.inputs = inputs;
    }

    public List<IndicatorIOParam> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<IndicatorIOParam> outputs) {
        this.outputs = outputs;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
