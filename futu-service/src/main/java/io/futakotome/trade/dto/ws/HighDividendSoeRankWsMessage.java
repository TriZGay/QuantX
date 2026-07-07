package io.futakotome.trade.dto.ws;

import com.futu.openapi.pb.QotOptionCommon;
import io.futakotome.trade.dto.message.HighDividendSoeRankContent;
import io.futakotome.trade.dto.message.Indicator;

import java.util.List;

public class HighDividendSoeRankWsMessage implements Message {
    private Integer sortField;          // SortField, 排序字段, 默认市值
    private Integer sortDir;           // SortDir, 排序方向, 默认降序
    private Integer offset;           // 起始位置, 默认0
    private Integer count;           // 返回数量 [1,200], 默认50
    private List<Indicator> filterList;     // 筛选条件(可覆盖默认PB/股息率条件)
    private HighDividendSoeRankContent content;

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

    public HighDividendSoeRankContent getContent() {
        return content;
    }

    public void setContent(HighDividendSoeRankContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.HIGH_DIVIDEND_SOE_RANK;
    }
}
