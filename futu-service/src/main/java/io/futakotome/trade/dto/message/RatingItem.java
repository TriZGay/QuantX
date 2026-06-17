package io.futakotome.trade.dto.message;

import com.google.gson.annotations.JsonAdapter;
import io.futakotome.trade.utils.converter.RatingItemConverter;

@JsonAdapter(RatingItemConverter.class)
public class RatingItem {
    private String analystUid;  // 分析师唯一标识
    private String institutionUid;  // 机构唯一标识
    private Integer rating; // 评级，仅返回 Sell(1)/Hold(3)/Buy(4)
    private String ratingStr;
    private Double targetPrice;  // 目标价
    private Long recommendationDate;  // 评级日期时间戳（秒）
    private String recommendationDateStr;  // 评级日期字符串，格式 YYYY-MM-DD
    private String ratingUrl;  // 评级来源 URL
    private Long updateTime;  // 更新时间戳（秒）
    private String updateTimeStr;  // 更新时间字符串，格式 YYYY-MM-DD

    public String getRatingStr() {
        return ratingStr;
    }

    public void setRatingStr(String ratingStr) {
        this.ratingStr = ratingStr;
    }

    public String getAnalystUid() {
        return analystUid;
    }

    public void setAnalystUid(String analystUid) {
        this.analystUid = analystUid;
    }

    public String getInstitutionUid() {
        return institutionUid;
    }

    public void setInstitutionUid(String institutionUid) {
        this.institutionUid = institutionUid;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Double getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(Double targetPrice) {
        this.targetPrice = targetPrice;
    }

    public Long getRecommendationDate() {
        return recommendationDate;
    }

    public void setRecommendationDate(Long recommendationDate) {
        this.recommendationDate = recommendationDate;
    }

    public String getRecommendationDateStr() {
        return recommendationDateStr;
    }

    public void setRecommendationDateStr(String recommendationDateStr) {
        this.recommendationDateStr = recommendationDateStr;
    }

    public String getRatingUrl() {
        return ratingUrl;
    }

    public void setRatingUrl(String ratingUrl) {
        this.ratingUrl = ratingUrl;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }
}
