package io.futakotome.trade.dto.message;

import java.util.List;

public class HighDividendSoeRankContent {
    private List<HighDividendSOERankItem>  dataList ;  // 数据列表
    private Integer allCount ;                    // 符合条件的总数据量

    public List<HighDividendSOERankItem> getDataList() {
        return dataList;
    }

    public void setDataList(List<HighDividendSOERankItem> dataList) {
        this.dataList = dataList;
    }

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }
}
