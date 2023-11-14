package io.futakotome.sec.controller.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UpdatePermissionRequest {
    @NotNull(message = "权限名称必填")
    @NotEmpty(message = "权限名称不能为空")
    private String name;
    @NotNull(message = "权限类型必填")
    private Integer type;
    @NotNull(message = "权限标识必填")
    @NotEmpty(message = "权限标识不能为空")
    private String tag;
    private String method;
    private Long menuId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}
