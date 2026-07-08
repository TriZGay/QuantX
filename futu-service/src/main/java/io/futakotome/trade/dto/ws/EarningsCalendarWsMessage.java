package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.EarningsCalendarContent;
import io.futakotome.trade.dto.message.Indicator;

import java.util.List;

public class EarningsCalendarWsMessage implements Message {
    private Integer market;      // Qot_Common.QotMarket (支持HK/US/CNSH/CNSZ/SG/JP/AU/CA)
    private Integer sortType;   // EarningsCalendarSortType (默认 Hot)
    private String beginDate;  // 开始日期，格式"yyyy-MM-dd"，不传默认今天(仅拉取当天)
    private String endDate;    // 结束日期，格式"yyyy-MM-dd"，不传则仅拉取beginDate当天；与beginDate间隔不超过7天
    private List<Indicator> filterList;  // 筛选条件(AND关系)
    private EarningsCalendarContent content;

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
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

    public List<Indicator> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<Indicator> filterList) {
        this.filterList = filterList;
    }

    public EarningsCalendarContent getContent() {
        return content;
    }

    public void setContent(EarningsCalendarContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.EARNINGS_CALENDAR;
    }
}
