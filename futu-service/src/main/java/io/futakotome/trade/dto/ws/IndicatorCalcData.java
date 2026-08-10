package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.CommonSecurity;
import io.futakotome.trade.dto.message.KLMessageContent;

import java.util.List;

public class IndicatorCalcData {
    private CommonSecurity security;
    private Integer klType;
    private List<KLMessageContent> kLine;

    public CommonSecurity getSecurity() {
        return security;
    }

    public void setSecurity(CommonSecurity security) {
        this.security = security;
    }

    public Integer getKlType() {
        return klType;
    }

    public void setKlType(Integer klType) {
        this.klType = klType;
    }

    public List<KLMessageContent> getkLine() {
        return kLine;
    }

    public void setkLine(List<KLMessageContent> kLine) {
        this.kLine = kLine;
    }
}
