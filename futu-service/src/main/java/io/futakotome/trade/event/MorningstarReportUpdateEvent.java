package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.MorningstarReportContent;

public class MorningstarReportUpdateEvent {
    private MorningstarReportContent content;

    public MorningstarReportUpdateEvent(MorningstarReportContent content) {
        this.content = content;
    }

    public MorningstarReportContent getContent() {
        return content;
    }

    public void setContent(MorningstarReportContent content) {
        this.content = content;
    }
}
