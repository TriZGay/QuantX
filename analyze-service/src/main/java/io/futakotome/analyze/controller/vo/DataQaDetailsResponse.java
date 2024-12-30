package io.futakotome.analyze.controller.vo;

import java.util.List;

public class DataQaDetailsResponse {
    List<RepeatDetail> repeatDetails;

    public List<RepeatDetail> getRepeatDetails() {
        return repeatDetails;
    }

    public void setRepeatDetails(List<RepeatDetail> repeatDetails) {
        this.repeatDetails = repeatDetails;
    }

    public static class RepeatDetail {
        private String checkDate;
        private String code;
        private Integer rehabType;
        private String updateTime;

        public String getCheckDate() {
            return checkDate;
        }

        public void setCheckDate(String checkDate) {
            this.checkDate = checkDate;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Integer getRehabType() {
            return rehabType;
        }

        public void setRehabType(Integer rehabType) {
            this.rehabType = rehabType;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
