package io.futakotome.trade.dto.message;

import java.util.List;

public class IndicatorInfo {
    private String shortName;
    private String fullName;
    private List<IndicatorInputParam> inputs;
    private List<IndicatorOutputParam> outputs;
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

    public List<IndicatorInputParam> getInputs() {
        return inputs;
    }

    public void setInputs(List<IndicatorInputParam> inputs) {
        this.inputs = inputs;
    }

    public List<IndicatorOutputParam> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<IndicatorOutputParam> outputs) {
        this.outputs = outputs;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
