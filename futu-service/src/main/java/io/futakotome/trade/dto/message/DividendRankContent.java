package io.futakotome.trade.dto.message;

import java.util.List;

public class DividendRankContent {
    private List<DividendRankItem> dataList;     // 数据列表

    public List<DividendRankItem> getDataList() {
        return dataList;
    }

    public void setDataList(List<DividendRankItem> dataList) {
        this.dataList = dataList;
    }
}
