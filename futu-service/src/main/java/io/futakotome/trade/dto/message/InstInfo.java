package io.futakotome.trade.dto.message;

public class InstInfo {
    private String institutionUid;
    private String institutionPictureUrl;
    private String institutionName;
    private Long updateTime;
    private String updateTimeStr;
    private String institutionSourceName;
    private String institutionEnName;

    public String getInstitutionUid() {
        return institutionUid;
    }

    public void setInstitutionUid(String institutionUid) {
        this.institutionUid = institutionUid;
    }

    public String getInstitutionPictureUrl() {
        return institutionPictureUrl;
    }

    public void setInstitutionPictureUrl(String institutionPictureUrl) {
        this.institutionPictureUrl = institutionPictureUrl;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
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

    public String getInstitutionSourceName() {
        return institutionSourceName;
    }

    public void setInstitutionSourceName(String institutionSourceName) {
        this.institutionSourceName = institutionSourceName;
    }

    public String getInstitutionEnName() {
        return institutionEnName;
    }

    public void setInstitutionEnName(String institutionEnName) {
        this.institutionEnName = institutionEnName;
    }
}
