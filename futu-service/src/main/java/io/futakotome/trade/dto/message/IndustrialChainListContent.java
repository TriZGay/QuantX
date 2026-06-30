package io.futakotome.trade.dto.message;

import java.util.List;

public class IndustrialChainListContent {
    private List<IndustrialChainInfo> dataList;
    private Integer allCount;
    private String nextPage;

    public List<IndustrialChainInfo> getDataList() {
        return dataList;
    }

    public void setDataList(List<IndustrialChainInfo> dataList) {
        this.dataList = dataList;
    }

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }
}
