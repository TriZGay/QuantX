package io.futakotome.trade.dto.message;

public class CommonTrdHeader {
    private Integer trdEnv;
    private String accID;
    private Integer trdMarket;

    public Integer getTrdEnv() {
        return trdEnv;
    }

    public void setTrdEnv(Integer trdEnv) {
        this.trdEnv = trdEnv;
    }

    public String getAccID() {
        return accID;
    }

    public void setAccID(String accID) {
        this.accID = accID;
    }

    public Integer getTrdMarket() {
        return trdMarket;
    }

    public void setTrdMarket(Integer trdMarket) {
        this.trdMarket = trdMarket;
    }
}
