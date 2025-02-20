package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.CapitalFlowMessageContent;
import io.futakotome.trade.dto.message.CommonSecurity;

import java.util.List;

public class CapitalFlowWsMessage implements Message {
    private CommonSecurity security;
    private Integer periodType;
    private String beginTime;
    private String endTime;

    private String lastValidTime;
    private List<CapitalFlowMessageContent> contentList;

    public String getLastValidTime() {
        return lastValidTime;
    }

    public void setLastValidTime(String lastValidTime) {
        this.lastValidTime = lastValidTime;
    }

    public List<CapitalFlowMessageContent> getContentList() {
        return contentList;
    }

    public void setContentList(List<CapitalFlowMessageContent> contentList) {
        this.contentList = contentList;
    }

    public CommonSecurity getSecurity() {
        return security;
    }

    public void setSecurity(CommonSecurity security) {
        this.security = security;
    }

    public Integer getPeriodType() {
        return periodType;
    }

    public void setPeriodType(Integer periodType) {
        this.periodType = periodType;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public MessageType getType() {
        return MessageType.CAPITAL_FLOW;
    }
}
