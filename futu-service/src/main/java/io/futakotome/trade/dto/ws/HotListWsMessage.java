package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.HotListContent;
import io.futakotome.trade.dto.message.Indicator;

import java.util.List;

public class HotListWsMessage implements Message {
    private Integer market;           // Qot_Common.QotMarket (HK=1, US=11)
    private Integer sortField;          // HotListSortField, 排序字段, 默认综合热度
    private Integer sortDir;           // SortDir, 排序方向, 默认降序
    private Integer offset;           // 起始位置, 默认0
    private Integer count;           // 返回数量 [1,200], 默认50
    private List<Indicator> filterList;     // 筛选条件(市值)
    private HotListContent content;

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public Integer getSortField() {
        return sortField;
    }

    public void setSortField(Integer sortField) {
        this.sortField = sortField;
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

    public HotListContent getContent() {
        return content;
    }

    public void setContent(HotListContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.HOT_RANK;
    }
}
