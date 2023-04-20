package io.futakotome.sub.controller;

import java.util.List;

public class SubscribeRequest {
    private List<SubscribeSecurity> securityList;
    private List<Integer> subTypeList;

    public SubscribeRequest(List<SubscribeSecurity> securityList, List<Integer> subTypeList) {
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
}
