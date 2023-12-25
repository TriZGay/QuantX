package io.futakotome.sec.controller.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddRoleRequest {
    @NotNull(message = "角色名称必填")
    @NotEmpty(message = "角色名称不能为空")
    private String name;

    @NotNull(message = "角色状态必填")
    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
