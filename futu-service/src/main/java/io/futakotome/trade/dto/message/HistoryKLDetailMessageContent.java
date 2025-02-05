package io.futakotome.trade.dto.message;

import java.util.List;

public class HistoryKLDetailMessageContent {
    private Integer usedQuota;
    private Integer remainQuota;
    private List<HistoryKLDetailItem> detailList;

    public Integer getUsedQuota() {
        return usedQuota;
    }

    public void setUsedQuota(Integer usedQuota) {
        this.usedQuota = usedQuota;
    }

    public Integer getRemainQuota() {
        return remainQuota;
    }

    public void setRemainQuota(Integer remainQuota) {
        this.remainQuota = remainQuota;
    }

    public List<HistoryKLDetailItem> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<HistoryKLDetailItem> detailList) {
        this.detailList = detailList;
    }
}
