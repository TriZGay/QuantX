package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.EarningsBeatRankContent;
import io.futakotome.trade.dto.message.Indicator;

import java.util.List;

public class EarningsBeatRankWsMessage implements Message {
    private Integer market;          // Qot_Common.QotMarket (仅支持 HK=1, US=11, SG=31, JP=41)
    private Integer beatType;        // BeatType
    private Integer count;           // 返回数量 [1,300], 默认30
    private Integer term;            // BeatTerm, 默认 All
    private List<Indicator> filterList;  // 可选筛选条件(AND关系), 不传则使用默认值(超预期>0, 时间=最近30天)
    private Integer sortField;       // SortField, 排序字段(固定降序), 默认按市值
    private EarningsBeatRankContent content;

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public Integer getBeatType() {
        return beatType;
    }

    public void setBeatType(Integer beatType) {
        this.beatType = beatType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
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

    public EarningsBeatRankContent getContent() {
        return content;
    }

    public void setContent(EarningsBeatRankContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.EARNINGS_BEAT_RANK;
    }
}
