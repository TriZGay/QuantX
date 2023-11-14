package io.futakotome.sec.controller.vo;

public class PermissionVo {
    private Long id;
    private String name;
    private Integer type;
    private String tag;
    private String method;
    private Long menuId;
    private String createTime;
    private String modifyTime;

    public PermissionVo(Long id, String name, Integer type, String tag, String method, Long menuId, String createTime, String modifyTime) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.tag = tag;
        this.method = method;
        this.menuId = menuId;
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
