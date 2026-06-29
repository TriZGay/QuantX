package io.futakotome.trade.dto.message;

import java.util.List;

public class InstitutionListContent {
    private List<InstitutionListItem> dataList ;  // 数据列表
    private Integer allCount ;                // 总数
    private String nextPage ;               // 下一页游标, 空=无更多
    private String currency ;               // 币种

    public List<InstitutionListItem> getDataList() {
        return dataList;
    }

    public void setDataList(List<InstitutionListItem> dataList) {
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
