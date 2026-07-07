package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.Indicator;
import io.futakotome.trade.dto.message.TopMoversRankContent;

import java.util.List;

public class TopMoversRankWsMessage implements Message {
    private Integer market;             // Qot_Common.QotMarket (HK=1, US=11)
    private Integer sortDir;            // SortDir, 排序方向, 默认降序(领涨)
    private Integer offset;             // 起始位置, 默认0
    private Integer count;              // 返回数量 [1,200], 默认50
    private List<Indicator> filterList;     // 筛选条件(AND关系)
    private TopMoversRankContent content;

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public Integer getSortDir() {
        return sortDir;
    }

    public void setSortDir(Integer sortDir) {
        this.sortDir = sortDir;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
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

    public TopMoversRankContent getContent() {
        return content;
    }

    public void setContent(TopMoversRankContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.TOP_MOVERS_RANK;
    }
}
