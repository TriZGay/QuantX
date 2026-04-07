package io.futakotome.trade.dto.message;

public class IpoData {
    private BasicIpoData basic;
    private CNIpoExData cnExData;
    private HKIpoExData hkExData;
    private USIpoExData usExData;

    public BasicIpoData getBasic() {
        return basic;
    }

    public void setBasic(BasicIpoData basic) {
        this.basic = basic;
    }

    public CNIpoExData getCnExData() {
        return cnExData;
    }

    public void setCnExData(CNIpoExData cnExData) {
        this.cnExData = cnExData;
    }

    public HKIpoExData getHkExData() {
        return hkExData;
    }

    public void setHkExData(HKIpoExData hkExData) {
        this.hkExData = hkExData;
    }

    public USIpoExData getUsExData() {
        return usExData;
    }

    public void setUsExData(USIpoExData usExData) {
        this.usExData = usExData;
    }
}
