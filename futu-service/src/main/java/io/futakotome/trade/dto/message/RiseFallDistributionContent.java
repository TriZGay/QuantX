package io.futakotome.trade.dto.message;

import java.util.List;

public class RiseFallDistributionContent {
    private CommonSecurity plate; //板块
    private List<RiseFallRange> rangeList; //涨跌分布区间列表

    public CommonSecurity getPlate() {
        return plate;
    }

    public void setPlate(CommonSecurity plate) {
        this.plate = plate;
    }

    public List<RiseFallRange> getRangeList() {
        return rangeList;
    }

    public void setRangeList(List<RiseFallRange> rangeList) {
        this.rangeList = rangeList;
    }
}
