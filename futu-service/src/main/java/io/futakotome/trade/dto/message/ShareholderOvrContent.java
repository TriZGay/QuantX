package io.futakotome.trade.dto.message;

import java.util.List;

public class ShareholderOvrContent {
    private List<OwnershipStaticInfo> mainHolderInfoList; // 主要股东列表
    private List<OwnershipStaticInfo> holderTypeInfoList; // 持股类型列表
    private List<HoldingPeriodItem> holdingPeriodList; // 可用报告期列表，仅当请求 periodId 为 0 时返回

    public List<OwnershipStaticInfo> getMainHolderInfoList() {
        return mainHolderInfoList;
    }

    public void setMainHolderInfoList(List<OwnershipStaticInfo> mainHolderInfoList) {
        this.mainHolderInfoList = mainHolderInfoList;
    }

    public List<OwnershipStaticInfo> getHolderTypeInfoList() {
        return holderTypeInfoList;
    }

    public void setHolderTypeInfoList(List<OwnershipStaticInfo> holderTypeInfoList) {
        this.holderTypeInfoList = holderTypeInfoList;
    }

    public List<HoldingPeriodItem> getHoldingPeriodList() {
        return holdingPeriodList;
    }

    public void setHoldingPeriodList(List<HoldingPeriodItem> holdingPeriodList) {
        this.holdingPeriodList = holdingPeriodList;
    }
}
