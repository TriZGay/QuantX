package io.futakotome.trade.controller.vo;

import io.futakotome.trade.domain.code.Firm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class UnlockRequest {
    //true 解锁 ;false 锁定
    @NotNull(message = "unlock不能为空")
    private Boolean unlock;
    @Null
    @EnumValid(target = Firm.class, message = "券商输入错误")
    private Integer firm;

    public Boolean getUnlock() {
        return unlock;
    }

    public void setUnlock(Boolean unlock) {
        this.unlock = unlock;
    }

    public Integer getFirm() {
        return firm;
    }

    public void setFirm(Integer firm) {
        this.firm = firm;
    }
}
