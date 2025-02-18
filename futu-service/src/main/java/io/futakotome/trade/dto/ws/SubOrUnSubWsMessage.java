package io.futakotome.trade.dto.ws;

import java.util.List;

public class SubOrUnSubWsMessage implements Message {
    private List<SubscribeSecurity> securityList;
    private List<Integer> subTypeList;

    private Boolean unsub;

    public SubOrUnSubWsMessage() {
    }

    public SubOrUnSubWsMessage(List<SubscribeSecurity> securityList, List<Integer> subTypeList) {
        this.securityList = securityList;
        this.subTypeList = subTypeList;
    }

    public List<SubscribeSecurity> getSecurityList() {
        return securityList;
    }

    public void setSecurityList(List<SubscribeSecurity> securityList) {
        this.securityList = securityList;
    }

    public List<Integer> getSubTypeList() {
        return subTypeList;
    }

    public void setSubTypeList(List<Integer> subTypeList) {
        this.subTypeList = subTypeList;
    }

    public Boolean getUnsub() {
        return unsub;
    }

    public void setUnsub(Boolean unsub) {
        this.unsub = unsub;
    }

    @Override
    public MessageType getType() {
        return MessageType.SUBSCRIPTION;
    }
}
