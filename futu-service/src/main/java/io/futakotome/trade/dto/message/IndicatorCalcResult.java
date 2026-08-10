package io.futakotome.trade.dto.message;

import java.util.List;

public class IndicatorCalcResult {
    private String calcId;                            //与发起请求时返回的 calcId 对应
    private List<IndicatorIOParam> outputs;  //输出线元数据
    private List<IndicatorOutputRow> outputRows;            //计算结果，按时间顺序

    public String getCalcId() {
        return calcId;
    }

    public void setCalcId(String calcId) {
        this.calcId = calcId;
    }

    public List<IndicatorIOParam> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<IndicatorIOParam> outputs) {
        this.outputs = outputs;
    }

    public List<IndicatorOutputRow> getOutputRows() {
        return outputRows;
    }

    public void setOutputRows(List<IndicatorOutputRow> outputRows) {
        this.outputRows = outputRows;
    }
}
