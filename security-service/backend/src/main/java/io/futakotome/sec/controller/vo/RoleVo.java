package io.futakotome.sec.controller.vo;

public class RoleVo {
    private Long id;
    private String name;
    private Integer status;
    private String createTime;
    private String modifyTime;

    public RoleVo() {
    }

    public RoleVo(Long id, String name, Integer status, String createTime, String modifyTime) {
        this.id = id;
        this.name = name;
        this.status = status;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
