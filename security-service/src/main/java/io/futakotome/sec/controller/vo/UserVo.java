package io.futakotome.sec.controller.vo;

public class UserVo {
    private Long id;
    private String username;
    private String password;
    private Integer status;
    private String createTime;
    private String modifyTime;

    public UserVo(Long id, String username, String password, Integer status, String createTime, String modifyTime) {
        this.id = id;
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
