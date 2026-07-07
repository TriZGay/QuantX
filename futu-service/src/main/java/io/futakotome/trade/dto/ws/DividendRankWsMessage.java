package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.DividendRankContent;
import io.futakotome.trade.dto.message.Indicator;

import java.util.List;

public class DividendRankWsMessage implements Message {
    private Integer market;              // Qot_Common.QotMarket (仅支持 HK=1, US=11, MY=61, SG=31, JP=41)
    private Integer rankType;            // DividendRankType
    private Integer count;               // 返回数量 [1,300], 默认10
    private List<Indicator> filterList;      // 可选筛选条件(AND关系)
    private Integer sortField;           // SortField, 排序字段(固定降序), 默认由rankType决定
    private DividendRankContent content;

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public Integer getRankType() {
        return rankType;
    }

    public void setRankType(Integer rankType) {
        this.rankType = rankType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Indicator> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<Indicator> filterList) {
        this.filterList = filterList;
    }

    public Integer getSortField() {
        return sortField;
    }

    public void setSortField(Integer sortField) {
        this.sortField = sortField;
    }

    public DividendRankContent getContent() {
        return content;
    }

    public void setContent(DividendRankContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.DIVIDEND_RANK;
    }
}
