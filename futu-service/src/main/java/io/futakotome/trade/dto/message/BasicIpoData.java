package io.futakotome.trade.dto.message;

public class BasicIpoData {
    private CommonSecurity security;
    private String name;
    private String listTime;
    private Double listTimestamp;

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

    public String getListTime() {
        return listTime;
    }

    public void setListTime(String listTime) {
        this.listTime = listTime;
    }

    public Double getListTimestamp() {
        return listTimestamp;
    }

    public void setListTimestamp(Double listTimestamp) {
        this.listTimestamp = listTimestamp;
    }
}
