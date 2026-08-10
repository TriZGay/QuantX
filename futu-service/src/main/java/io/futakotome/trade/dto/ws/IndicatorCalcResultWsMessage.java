package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.IndicatorCalcResult;

public class IndicatorCalcResultWsMessage implements Message {
    private IndicatorCalcResult result;

    public IndicatorCalcResult getResult() {
        return result;
    }

    public void setResult(IndicatorCalcResult result) {
        this.result = result;
    }

    @Override
    public MessageType getType() {
        return MessageType.INDICATOR_CALC_RESULT;
    }
}
