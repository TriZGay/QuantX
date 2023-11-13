package io.futakotome.sec.controller.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UpdateMenuRequest {
    @NotNull(message = "菜单名称必填")
    @NotEmpty(message = "菜单名称不能为空")
    private String name;
    private Long parentId;
    @NotNull(message = "菜单路径必填")
    @NotEmpty(message = "菜单路径不能为空")
    private String path;
    private String component;
    private String redirect;
    private String icon;
    private Integer visible;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }
}
