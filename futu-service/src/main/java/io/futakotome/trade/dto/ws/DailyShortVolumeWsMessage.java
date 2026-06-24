package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.DailyShotVolumeContent;

public class DailyShortVolumeWsMessage implements Message {
    private Integer market;
    private String code;
    private String nextKey;
    private Integer num;
    private DailyShotVolumeContent content;

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNextKey() {
        return nextKey;
    }

    public void setNextKey(String nextKey) {
        this.nextKey = nextKey;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public DailyShotVolumeContent getContent() {
        return content;
    }

    public void setContent(DailyShotVolumeContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.DAILY_SHORT_VOLUME;
    }
}
