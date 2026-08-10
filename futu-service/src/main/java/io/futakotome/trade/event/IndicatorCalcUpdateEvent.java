package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.IndicatorCalcResult;

public class IndicatorCalcUpdateEvent {
    private IndicatorCalcResult content;

    public IndicatorCalcUpdateEvent(IndicatorCalcResult content) {
        this.content = content;
    }

    public IndicatorCalcResult getContent() {
        return content;
    }

    public void setContent(IndicatorCalcResult content) {
        this.content = content;
    }
}
