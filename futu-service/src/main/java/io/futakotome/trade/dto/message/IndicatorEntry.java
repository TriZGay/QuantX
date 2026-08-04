package io.futakotome.trade.dto.message;

public class IndicatorEntry {
    private IndicatorInfo myLang;
    private IndicatorInfo python;

    public IndicatorInfo getMyLang() {
        return myLang;
    }

    public void setMyLang(IndicatorInfo myLang) {
        this.myLang = myLang;
    }

    public IndicatorInfo getPython() {
        return python;
    }

    public void setPython(IndicatorInfo python) {
        this.python = python;
    }
}
