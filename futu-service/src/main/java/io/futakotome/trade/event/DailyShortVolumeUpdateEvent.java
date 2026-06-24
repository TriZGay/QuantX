package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.DailyShotVolumeContent;

public class DailyShortVolumeUpdateEvent {
    private DailyShotVolumeContent content;

    public DailyShortVolumeUpdateEvent(DailyShotVolumeContent content) {
        this.content = content;
    }

    public DailyShotVolumeContent getContent() {
        return content;
    }

    public void setContent(DailyShotVolumeContent content) {
        this.content = content;
    }
}
