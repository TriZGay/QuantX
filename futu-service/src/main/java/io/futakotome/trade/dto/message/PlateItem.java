package io.futakotome.trade.dto.message;

public class PlateItem {
    private CommonSecurity security;  // 股票
    private String name;  // 板块名称

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
