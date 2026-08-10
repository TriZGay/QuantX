package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.IndicatorCalcResult;

public class IndicatorCalcResultUpdateEvent {
    private IndicatorCalcResult content;

    public IndicatorCalcResultUpdateEvent(IndicatorCalcResult content) {
        this.content = content;
    }

    public IndicatorCalcResult getContent() {
        return content;
    }

    public void setContent(IndicatorCalcResult content) {
        this.content = content;
    }
}
