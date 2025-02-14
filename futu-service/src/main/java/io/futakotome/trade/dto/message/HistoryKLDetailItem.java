package io.futakotome.trade.dto.message;

public class HistoryKLDetailItem {
    private CommonSecurity security;
    private String name;
    private String requestTime;
    private Long requestTimeStamp;

    public CommonSecurity getSecurity() {
        return security;
    }

    public void setSecurity(CommonSecurity security) {
        this.security = security;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public Long getRequestTimeStamp() {
        return requestTimeStamp;
    }

    public void setRequestTimeStamp(Long requestTimeStamp) {
        this.requestTimeStamp = requestTimeStamp;
    }
}
