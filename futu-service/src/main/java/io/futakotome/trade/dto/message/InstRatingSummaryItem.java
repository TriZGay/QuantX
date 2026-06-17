package io.futakotome.trade.dto.message;

import java.util.List;

public class InstRatingSummaryItem {
    private InstInfo institutionInfo;  // 机构信息
    private List<RatingItem> ratingItemList;  // 该机构对该股票的评级记录列表

    public InstInfo getInstitutionInfo() {
        return institutionInfo;
    }

    public void setInstitutionInfo(InstInfo institutionInfo) {
        this.institutionInfo = institutionInfo;
    }

    public List<RatingItem> getRatingItemList() {
        return ratingItemList;
    }

    public void setRatingItemList(List<RatingItem> ratingItemList) {
        this.ratingItemList = ratingItemList;
    }
}
