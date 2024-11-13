package io.futakotome.sec.domain;

import io.futakotome.sec.controller.vo.AddPermissionRequest;
import io.futakotome.sec.controller.vo.PermissionVo;
import io.futakotome.sec.controller.vo.UpdatePermissionRequest;
import io.futakotome.sec.dto.PermissionDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Permission {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-dd-mm HH:mm:ss");
    private Long id;
    private String name;
    private Integer type;
    private String tag;
    private String method;
    private Long menuId;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;

    public Permission() {
    }

    public Permission(Long id, String name, Integer type, String tag, String method, Long menuId, LocalDateTime createTime, LocalDateTime modifyTime) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.tag = tag;
        this.method = method;
        this.menuId = menuId;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
    }

    public static PermissionDto transferAddReq(AddPermissionRequest addPermissionRequest) {
        PermissionDto dto = new PermissionDto();
        dto.setName(addPermissionRequest.getName());
        dto.setType(addPermissionRequest.getType());
        dto.setTag(addPermissionRequest.getTag());
        dto.setMethod(addPermissionRequest.getMethod());
        dto.setMenuId(addPermissionRequest.getMenuId());
        dto.setCreateTime(LocalDateTime.now());
        dto.setModifyTime(LocalDateTime.now());
        return dto;
    }

    public static PermissionDto transferUpdReq(Long id, UpdatePermissionRequest updatePermissionRequest) {
        PermissionDto dto = new PermissionDto();
        dto.setId(id);
        dto.setName(updatePermissionRequest.getName());
        dto.setType(updatePermissionRequest.getType());
        dto.setTag(updatePermissionRequest.getTag());
        dto.setMethod(updatePermissionRequest.getMethod());
        dto.setMenuId(updatePermissionRequest.getMenuId());
        dto.setModifyTime(LocalDateTime.now());
        return dto;
    }

    public static Permission dto2Permission(PermissionDto dto) {
        return new Permission(dto.getId(), dto.getName(), dto.getType(), dto.getTag(), dto.getMethod(), dto.getMenuId(),
                dto.getCreateTime(), dto.getModifyTime());
    }

    public static PermissionVo permission2Vo(Permission permission) {
        return new PermissionVo(permission.getId(), permission.getName(), permission.getType(), permission.getTag(), permission.getMethod(), permission.getMenuId(),
                permission.getCreateTime().format(DATE_TIME_FORMATTER), permission.getModifyTime().format(DATE_TIME_FORMATTER));
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
