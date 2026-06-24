package io.futakotome.trade.dto.message;

public class DirectorInfo {
    private String displayLeaderName;  // 高管展示名称（仅用于展示，不用于查询背景接口）
    private String leaderName;  // 高管姓名（可传入 GetCompanyExecutiveBackground 查询背景）
    private String positionName;  // 职位名称
    private Long beginDate;// 任职起始日时间戳（秒）
    private String beginDateStr;  // 任职起始日字符串，格式 YYYY-MM-DD，对应市场时区
    private String leaderGender;  // 性别，如 "Male" / "Female"
    private String leaderAge;  // 年龄，字符串形式，如 "62"
    private String highestEducation;  // 最高学历
    private Long annualSalary;// 年薪
    private Long issueDate; // 发布日期时间戳（秒）
    private String issueDateStr; // 发布日期字符串，格式 YYYY-MM-DD，对应市场时区

    public String getDisplayLeaderName() {
        return displayLeaderName;
    }

    public void setDisplayLeaderName(String displayLeaderName) {
        this.displayLeaderName = displayLeaderName;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Long getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Long beginDate) {
        this.beginDate = beginDate;
    }

    public String getBeginDateStr() {
        return beginDateStr;
    }

    public void setBeginDateStr(String beginDateStr) {
        this.beginDateStr = beginDateStr;
    }

    public String getLeaderGender() {
        return leaderGender;
    }

    public void setLeaderGender(String leaderGender) {
        this.leaderGender = leaderGender;
    }

    public String getLeaderAge() {
        return leaderAge;
    }

    public void setLeaderAge(String leaderAge) {
        this.leaderAge = leaderAge;
    }

    public String getHighestEducation() {
        return highestEducation;
    }

    public void setHighestEducation(String highestEducation) {
        this.highestEducation = highestEducation;
    }

    public Long getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(Long annualSalary) {
        this.annualSalary = annualSalary;
    }

    public Long getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Long issueDate) {
        this.issueDate = issueDate;
    }

    public String getIssueDateStr() {
        return issueDateStr;
    }

    public void setIssueDateStr(String issueDateStr) {
        this.issueDateStr = issueDateStr;
    }
}
