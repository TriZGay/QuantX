package io.futakotome.sec.controller.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddUserRequest {
    @NotNull(message = "用户名必填")
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotNull(message = "密码必填")
    @NotEmpty(message = "密码不能为空")
    private String password;
    private Integer status;

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
}
