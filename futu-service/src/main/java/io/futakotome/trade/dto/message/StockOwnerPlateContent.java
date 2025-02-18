package io.futakotome.trade.dto.message;

import java.util.List;

public class StockOwnerPlateContent {
    private CommonSecurity security;
    private List<PlateInfoContent> plateInfoList;

    public CommonSecurity getSecurity() {
        return security;
    }

    public void setSecurity(CommonSecurity security) {
        this.security = security;
    }

    public List<PlateInfoContent> getPlateInfoList() {
        return plateInfoList;
    }

    public void setPlateInfoList(List<PlateInfoContent> plateInfoList) {
        this.plateInfoList = plateInfoList;
    }
}
