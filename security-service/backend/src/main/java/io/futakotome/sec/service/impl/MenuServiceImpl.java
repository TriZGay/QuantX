package io.futakotome.sec.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.sec.controller.vo.AddMenuRequest;
import io.futakotome.sec.controller.vo.ListMenuRequest;
import io.futakotome.sec.controller.vo.UpdateMenuRequest;
import io.futakotome.sec.domain.Menu;
import io.futakotome.sec.dto.MenuDto;
import io.futakotome.sec.mapper.MenuDtoMapper;
import io.futakotome.sec.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pc
 * @description 针对表【t_menu】的数据库操作Service实现
 * @createDate 2023-11-08 14:36:09
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuDtoMapper, MenuDto>
        implements MenuService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);

    private final MenuDtoMapper menuDtoMapper;

    public MenuServiceImpl(MenuDtoMapper menuDtoMapper) {
        this.menuDtoMapper = menuDtoMapper;
    }

    @Override
    public IPage<Menu> queryMenus(ListMenuRequest listMenuRequest) {
        try {
            Page<MenuDto> pagination = Page.of(1, 10);
            QueryWrapper<MenuDto> queryWrapper = Wrappers.query();
            if (listMenuRequest.getCurrent() != null) {
                pagination.setCurrent(listMenuRequest.getCurrent());
            }
            if (listMenuRequest.getSize() != null) {
                pagination.setSize(listMenuRequest.getSize());
            }
            if (listMenuRequest.getName() != null) {
                queryWrapper.like("name", listMenuRequest.getName());
            }
            return page(pagination, queryWrapper).convert(Menu::dto2MenuMapper);
        } catch (Exception e) {
            LOGGER.error("查询菜单失败", e);
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateMenuById(Long id, UpdateMenuRequest updateMenuRequest) {
        MenuDto updateMenu = Menu.transferUpdReq(id, updateMenuRequest);
        try {
            return menuDtoMapper.updateSelective(updateMenu) > 0;
        } catch (Exception e) {
            LOGGER.error("更新菜单失败", e);
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addMenu(AddMenuRequest addMenuRequest) {
        MenuDto addMenu = Menu.transferAddReq(addMenuRequest);
        try {
            return menuDtoMapper.insertAll(addMenu) > 0;
        } catch (Exception e) {
            LOGGER.error("新增菜单失败", e);
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBy(Long id) {
        try {
            return removeById(id);
        } catch (Exception e) {
            LOGGER.error("删除菜单出错", e);
            return false;
        }
    }
}




