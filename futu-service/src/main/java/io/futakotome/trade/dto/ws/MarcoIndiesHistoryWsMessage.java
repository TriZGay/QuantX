package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.MarcoIndiesHistoryContent;

public class MarcoIndiesHistoryWsMessage implements Message {
    private Long indicatorId;  //宏观指标ID(来自Qot_GetMacroIndicatorList返回)
    private String time;         //时间节点 "yyyy-MM-dd"，从该时间往前拉取；不传默认当前时间
    private Integer maxCount;      //拉取条数，默认100，上限1000
    private MarcoIndiesHistoryContent content;

    public Long getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(Long indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public MarcoIndiesHistoryContent getContent() {
        return content;
    }

    public void setContent(MarcoIndiesHistoryContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.MARCO_INDIES_HISTORY;
    }
}
