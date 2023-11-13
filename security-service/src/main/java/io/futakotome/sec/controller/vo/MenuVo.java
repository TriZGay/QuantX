package io.futakotome.sec.controller.vo;

public class MenuVo {
    private Long id;
    private String name;
    private Long parentId;
    private String path;
    private String component;
    private String redirect;
    private String icon;
    private Integer visible;
    private String createTime;
    private String modifyTime;

    public MenuVo(Long id, String name, Long parentId, String path, String component, String redirect, String icon, Integer visible, String createTime, String modifyTime) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.path = path;
        this.component = component;
        this.redirect = redirect;
        this.icon = icon;
        this.visible = visible;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }
}
