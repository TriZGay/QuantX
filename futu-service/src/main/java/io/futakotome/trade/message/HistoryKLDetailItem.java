package io.futakotome.trade.message;

public class HistoryKLDetailItem {
    private BasicQuoteSecurity security;
    private String name;
    private String requestTime;
    private Long requestTimeStamp;

    public BasicQuoteSecurity getSecurity() {
        return security;
    }

    public void setSecurity(BasicQuoteSecurity security) {
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
