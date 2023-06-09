package io.futakotome.trade.controller.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class SubscribeRequest {
    @NotNull
    @Valid
    private List<SubscribeSecurity> securityList;
    @NotNull
    private List<Integer> subTypeList;

    private Boolean unsub;

    public SubscribeRequest() {
    }

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

    public Boolean isUnsub() {
        return unsub;
    }

    public void setUnsub(Boolean unsub) {
        this.unsub = unsub;
    }
}
