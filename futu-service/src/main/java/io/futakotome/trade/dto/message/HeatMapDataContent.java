package io.futakotome.trade.dto.message;

import java.util.List;

public class HeatMapDataContent {
    private List<HeatMapPlateData> plateDataList; //板块数据列表
    private Integer allCount; //板块总数
    private String nextPage; //下一页游标,空表示最后一页

    public List<HeatMapPlateData> getPlateDataList() {
        return plateDataList;
    }

    public void setPlateDataList(List<HeatMapPlateData> plateDataList) {
        this.plateDataList = plateDataList;
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
