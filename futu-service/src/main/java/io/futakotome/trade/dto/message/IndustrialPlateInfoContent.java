package io.futakotome.trade.dto.message;

public class IndustrialPlateInfoContent {
    private Long plateId;             // 板块ID
    private String summary;            // 板块简介

    public Long getPlateId() {
        return plateId;
    }

    public void setPlateId(Long plateId) {
        this.plateId = plateId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
