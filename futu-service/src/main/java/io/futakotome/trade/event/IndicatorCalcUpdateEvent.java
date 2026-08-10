package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.IndicatorCalcReqResult;

public class IndicatorCalcUpdateEvent {
    private IndicatorCalcReqResult content;

    public IndicatorCalcUpdateEvent(IndicatorCalcReqResult content) {
        this.content = content;
    }

    public IndicatorCalcReqResult getContent() {
        return content;
    }

    public void setContent(IndicatorCalcReqResult content) {
        this.content = content;
    }
}
