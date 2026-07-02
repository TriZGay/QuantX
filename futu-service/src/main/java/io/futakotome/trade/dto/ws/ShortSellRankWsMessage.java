package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.CommonSecurity;
import io.futakotome.trade.dto.message.ShortSellRankContent;

import java.util.List;

public class ShortSellRankWsMessage implements Message {
    private Integer market;             // Qot_Common.QotMarket, 市场(HK=1, US=11), 默认US
    private Integer sortField;          // ShortSellingSortField, 排序字段, 默认卖空变化量
    private Integer sortDir;            // SortDir, 排序方向, 默认降序
    private Integer offset;             // 起始位置, 默认0
    private Integer count;              // 返回数量 [1,35], 默认10
    private List<CommonSecurity> plateList;  // 行业板块列表, 空=全部
    private ShortSellRankContent content;

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

    public List<CommonSecurity> getPlateList() {
        return plateList;
    }

    public void setPlateList(List<CommonSecurity> plateList) {
        this.plateList = plateList;
    }

    public ShortSellRankContent getContent() {
        return content;
    }

    public void setContent(ShortSellRankContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.SHORT_SELL_RANK;
    }
}
