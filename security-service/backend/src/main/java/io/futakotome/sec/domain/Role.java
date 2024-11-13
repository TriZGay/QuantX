package io.futakotome.sec.domain;

import io.futakotome.sec.controller.vo.AddRoleRequest;
import io.futakotome.sec.controller.vo.RoleVo;
import io.futakotome.sec.controller.vo.UpdateRoleRequest;
import io.futakotome.sec.dto.RoleDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Role {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-dd-mm HH:mm:ss");
    private Long id;
    private String name;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;

    public Role() {
    }

    public Role(Long id, String name, Integer status, LocalDateTime createTime, LocalDateTime modifyTime) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
    }

    public static RoleDto transferAddReq(AddRoleRequest addRoleRequest) {
        RoleDto roleDto = new RoleDto();
        roleDto.setName(addRoleRequest.getName());
        roleDto.setStatus(addRoleRequest.getStatus());
        roleDto.setCreateTime(LocalDateTime.now());
        roleDto.setModifyTime(LocalDateTime.now());
        return roleDto;
    }

    public static RoleDto transferUpdReq(Long id, UpdateRoleRequest updateRoleRequest) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(id);
        roleDto.setName(updateRoleRequest.getName());
        roleDto.setStatus(updateRoleRequest.getStatus());
        roleDto.setModifyTime(LocalDateTime.now());
        return roleDto;
    }

    public static Role dto2RoleMapper(RoleDto roleDto) {
        return new Role(roleDto.getId(), roleDto.getName(), roleDto.getStatus(), roleDto.getCreateTime(), roleDto.getModifyTime());
    }

    public static RoleVo role2VoMapper(Role role) {
        return new RoleVo(role.getId(), role.getName(), role.getStatus(),
                role.getCreateTime().format(DATE_TIME_FORMATTER), role.getModifyTime().format(DATE_TIME_FORMATTER));
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
