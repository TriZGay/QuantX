package io.futakotome.sec.domain;

import io.futakotome.sec.controller.vo.AddMenuRequest;
import io.futakotome.sec.controller.vo.MenuVo;
import io.futakotome.sec.controller.vo.UpdateMenuRequest;
import io.futakotome.sec.dto.MenuDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Menu {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-dd-mm HH:mm:ss");
    private Long id;
    private String name;
    private Long parentId;
    private String path;
    private String component;
    private String redirect;
    private String icon;
    private Integer visible;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;

    public Menu() {
    }

    public Menu(Long id, String name, Long parentId, String path, String component, String redirect, String icon, Integer visible, LocalDateTime createTime, LocalDateTime modifyTime) {
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

    public static MenuDto transferAddReq(AddMenuRequest addMenuRequest) {
        MenuDto menuDto = new MenuDto();
        menuDto.setName(addMenuRequest.getName());
        menuDto.setParentId(addMenuRequest.getParentId());
        menuDto.setPath(addMenuRequest.getPath());
        menuDto.setComponent(addMenuRequest.getComponent());
        menuDto.setRedirect(addMenuRequest.getRedirect());
        menuDto.setIcon(addMenuRequest.getIcon());
        menuDto.setVisible(addMenuRequest.getVisible());
        menuDto.setCreateTime(LocalDateTime.now());
        menuDto.setModifyTime(LocalDateTime.now());
        return menuDto;
    }

    public static MenuDto transferUpdReq(Long id, UpdateMenuRequest updateMenuRequest) {
        MenuDto menuDto = new MenuDto();
        menuDto.setId(id);
        menuDto.setName(updateMenuRequest.getName());
        menuDto.setParentId(updateMenuRequest.getParentId());
        menuDto.setPath(updateMenuRequest.getPath());
        menuDto.setComponent(updateMenuRequest.getComponent());
        menuDto.setRedirect(updateMenuRequest.getRedirect());
        menuDto.setIcon(updateMenuRequest.getIcon());
        menuDto.setVisible(updateMenuRequest.getVisible());
        menuDto.setModifyTime(LocalDateTime.now());
        return menuDto;
    }

    public static Menu dto2MenuMapper(MenuDto menuDto) {
        return new Menu(menuDto.getId(), menuDto.getName(), menuDto.getParentId(),
                menuDto.getPath(), menuDto.getComponent(), menuDto.getRedirect(),
                menuDto.getIcon(), menuDto.getVisible(), menuDto.getCreateTime(), menuDto.getModifyTime());
    }

    public static MenuVo menu2VoMapper(Menu menu) {
        return new MenuVo(menu.getId(), menu.getName(), menu.getParentId(),
                menu.getPath(), menu.getComponent(), menu.getRedirect(),
                menu.getIcon(), menu.getVisible(), menu.getCreateTime().format(DATE_TIME_FORMATTER), menu.getModifyTime().format(DATE_TIME_FORMATTER));
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
