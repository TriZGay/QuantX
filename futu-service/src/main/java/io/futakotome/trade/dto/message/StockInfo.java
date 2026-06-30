package io.futakotome.trade.dto.message;

public class StockInfo {
    private CommonSecurity security; //股票
    private String name; //股票名称

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
}
