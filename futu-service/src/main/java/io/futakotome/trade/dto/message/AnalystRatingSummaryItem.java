package io.futakotome.trade.dto.message;

import java.util.List;

public class AnalystRatingSummaryItem {
    private AnalystInfo analystInfo;  // 分析师信息
    private List<RatingItem> ratingItemList;  // 该分析师对该股票的评级记录列表

    public AnalystInfo getAnalystInfo() {
        return analystInfo;
    }

    public void setAnalystInfo(AnalystInfo analystInfo) {
        this.analystInfo = analystInfo;
    }

    public List<RatingItem> getRatingItemList() {
        return ratingItemList;
    }

    public void setRatingItemList(List<RatingItem> ratingItemList) {
        this.ratingItemList = ratingItemList;
    }
}
