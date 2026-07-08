package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.DividendCalendarContent;

public class DividendCalendarWsMessage implements Message {
    private Integer market;          // Qot_Common.QotMarket (支持 HK=1, US=11, MY=61, SG=31, JP=41)
    private String date;           // 查询日期, 格式"YYYY-MM-DD"
    private Integer dataFrom;        // 分页偏移量, 默认0
    private Integer count;           // 返回数量, 默认不限

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getDataFrom() {
        return dataFrom;
    }

    public void setDataFrom(Integer dataFrom) {
        this.dataFrom = dataFrom;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    private DividendCalendarContent content;

    public DividendCalendarContent getContent() {
        return content;
    }

    public void setContent(DividendCalendarContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.DIVIDEND_CALENDAR;
    }
}
