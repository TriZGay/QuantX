package io.futakotome.trade.dto.message;

import java.util.List;

public class InstRatingDetail {
    private InstInfo institutionInfo;
    private List<AnalystInfo> analystInfoList;
    private List<RatingItem> ratingItemList;

    public InstInfo getInstitutionInfo() {
        return institutionInfo;
    }

    public void setInstitutionInfo(InstInfo institutionInfo) {
        this.institutionInfo = institutionInfo;
    }

    public List<AnalystInfo> getAnalystInfoList() {
        return analystInfoList;
    }

    public void setAnalystInfoList(List<AnalystInfo> analystInfoList) {
        this.analystInfoList = analystInfoList;
    }

    public List<RatingItem> getRatingItemList() {
        return ratingItemList;
    }

    public void setRatingItemList(List<RatingItem> ratingItemList) {
        this.ratingItemList = ratingItemList;
    }
}
