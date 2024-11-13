package io.futakotome.sec.domain;

import io.futakotome.sec.controller.vo.AddUserRequest;
import io.futakotome.sec.controller.vo.UpdateUserRequest;
import io.futakotome.sec.controller.vo.UserVo;
import io.futakotome.sec.dto.UserDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-dd-mm HH:mm:ss");
    private Long id;
    private String username;
    private String password;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;

    public User(Long id, String username, String password, Integer status, LocalDateTime createTime, LocalDateTime modifyTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
    }

    public static UserDto transferAddReq(AddUserRequest request) {
        UserDto dto = new UserDto();
        dto.setUsername(request.getUsername());
        dto.setPassword(request.getPassword());
        dto.setStatus(request.getStatus());
        dto.setCreateTime(LocalDateTime.now());
        dto.setModifyTime(LocalDateTime.now());
        return dto;
    }

    public static UserDto transferUpdReq(Long id, UpdateUserRequest request) {
        UserDto dto = new UserDto();
        dto.setId(id);
        dto.setUsername(request.getUsername());
        dto.setPassword(request.getPassword());
        dto.setStatus(request.getStatus());
        dto.setModifyTime(LocalDateTime.now());
        return dto;
    }

    public static User dto2UserMapper(UserDto dto) {
        return new User(dto.getId(), dto.getUsername(), dto.getPassword(), dto.getStatus(),
                dto.getCreateTime(), dto.getModifyTime());
    }

    public static UserVo user2VoMapper(User user) {
        return new UserVo(user.getId(), user.getUsername(), user.getPassword(), user.getStatus(),
                user.getCreateTime().format(DATE_TIME_FORMATTER), user.getModifyTime().format(DATE_TIME_FORMATTER));
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }
}
