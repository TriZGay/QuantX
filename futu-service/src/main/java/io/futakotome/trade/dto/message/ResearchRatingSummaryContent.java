package io.futakotome.trade.dto.message;

import java.util.List;

public class ResearchRatingSummaryContent {
    private List<InstRatingSummaryItem> instRatingSummaryList;
    private List<AnalystRatingSummaryItem> analystRatingSummaryList;
    private InstRatingDetail instRatingDetail;
    private AnalystRatingDetail analystRatingDetail;
    private String nextKey;

    public List<InstRatingSummaryItem> getInstRatingSummaryList() {
        return instRatingSummaryList;
    }

    public void setInstRatingSummaryList(List<InstRatingSummaryItem> instRatingSummaryList) {
        this.instRatingSummaryList = instRatingSummaryList;
    }

    public List<AnalystRatingSummaryItem> getAnalystRatingSummaryList() {
        return analystRatingSummaryList;
    }

    public void setAnalystRatingSummaryList(List<AnalystRatingSummaryItem> analystRatingSummaryList) {
        this.analystRatingSummaryList = analystRatingSummaryList;
    }

    public InstRatingDetail getInstRatingDetail() {
        return instRatingDetail;
    }

    public void setInstRatingDetail(InstRatingDetail instRatingDetail) {
        this.instRatingDetail = instRatingDetail;
    }

    public AnalystRatingDetail getAnalystRatingDetail() {
        return analystRatingDetail;
    }

    public void setAnalystRatingDetail(AnalystRatingDetail analystRatingDetail) {
        this.analystRatingDetail = analystRatingDetail;
    }

    public String getNextKey() {
        return nextKey;
    }

    public void setNextKey(String nextKey) {
        this.nextKey = nextKey;
    }
}
