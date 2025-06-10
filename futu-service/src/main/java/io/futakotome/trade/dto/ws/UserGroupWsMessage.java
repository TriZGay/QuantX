package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.GroupData;

import java.util.List;

public class UserGroupWsMessage {
    List<GroupData> groupDataList;

    public List<GroupData> getGroupDataList() {
        return groupDataList;
    }

    public void setGroupDataList(List<GroupData> groupDataList) {
        this.groupDataList = groupDataList;
    }
}
