package io.futakotome.trade.dto.message;

import java.util.List;

public class AnalystRatingDetail {
    private AnalystInfo analystInfo;
    private List<RatingItem> ratingItemList;

    public List<RatingItem> getRatingItemList() {
        return ratingItemList;
    }

    public void setRatingItemList(List<RatingItem> ratingItemList) {
        this.ratingItemList = ratingItemList;
    }

    public AnalystInfo getAnalystInfo() {
        return analystInfo;
    }

    public void setAnalystInfo(AnalystInfo analystInfo) {
        this.analystInfo = analystInfo;
    }
}
