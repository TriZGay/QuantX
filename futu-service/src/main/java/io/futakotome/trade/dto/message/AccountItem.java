package io.futakotome.trade.dto.message;

import java.util.List;

public class AccountItem {
    private Integer trdEnv;
    private String trdEnvStr;
    private String accID;
    private List<Integer> trdMarketAuthList;
    private Integer accType;
    private String accTypeStr;
    private String cardNum;
    private Integer securityFirm;
    private String securityFirmStr;
    private Integer simAccType;
    private String simAccTypeStr;
    private String uniCardNum;
    private Integer accStatus;
    private String accStatusStr;

    public String getTrdEnvStr() {
        return trdEnvStr;
    }

    public void setTrdEnvStr(String trdEnvStr) {
        this.trdEnvStr = trdEnvStr;
    }

    public List<Integer> getTrdMarketAuthList() {
        return trdMarketAuthList;
    }

    public void setTrdMarketAuthList(List<Integer> trdMarketAuthList) {
        this.trdMarketAuthList = trdMarketAuthList;
    }

    public String getAccTypeStr() {
        return accTypeStr;
    }

    public void setAccTypeStr(String accTypeStr) {
        this.accTypeStr = accTypeStr;
    }

    public String getSecurityFirmStr() {
        return securityFirmStr;
    }

    public void setSecurityFirmStr(String securityFirmStr) {
        this.securityFirmStr = securityFirmStr;
    }

    public String getSimAccTypeStr() {
        return simAccTypeStr;
    }

    public void setSimAccTypeStr(String simAccTypeStr) {
        this.simAccTypeStr = simAccTypeStr;
    }

    public String getAccStatusStr() {
        return accStatusStr;
    }

    public void setAccStatusStr(String accStatusStr) {
        this.accStatusStr = accStatusStr;
    }

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

    public Integer getAccType() {
        return accType;
    }

    public void setAccType(Integer accType) {
        this.accType = accType;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public Integer getSecurityFirm() {
        return securityFirm;
    }

    public void setSecurityFirm(Integer securityFirm) {
        this.securityFirm = securityFirm;
    }

    public Integer getSimAccType() {
        return simAccType;
    }

    public void setSimAccType(Integer simAccType) {
        this.simAccType = simAccType;
    }

    public String getUniCardNum() {
        return uniCardNum;
    }

    public void setUniCardNum(String uniCardNum) {
        this.uniCardNum = uniCardNum;
    }

    public Integer getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(Integer accStatus) {
        this.accStatus = accStatus;
    }
}
