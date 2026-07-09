package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.EconomicCalendarContent;

import java.util.List;

public class EconomicCalendarWsMessage implements Message {
    private String beginDate;      // 开始日期，格式"yyyy-MM-dd"，按本机系统时区解释
    private String endDate;        // 结束日期，格式"yyyy-MM-dd"，不传则仅查beginDate当天
    private List<Integer> marketList;      // Qot_Common.QotMarket，市场筛选(多选，支持HK/US/CNSH/SG/JP/AU/MY/CA)
    private Integer importance;      // Importance，事件重要性筛选，默认All
    private Integer count;           // 每页数量，默认50，最大100
    private String nextPage;       // 翻页标记，首次不传，后续带上S2C返回的nextPage
    private EconomicCalendarContent content;

    public List<Integer> getMarketList() {
        return marketList;
    }

    public void setMarketList(List<Integer> marketList) {
        this.marketList = marketList;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public Integer getImportance() {
        return importance;
    }

    public void setImportance(Integer importance) {
        this.importance = importance;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public EconomicCalendarContent getContent() {
        return content;
    }

    public void setContent(EconomicCalendarContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.ECONOMIC_CALENDAR;
    }
}
