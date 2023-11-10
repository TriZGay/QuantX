package io.futakotome.sec.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.sec.controller.vo.AddMenuRequest;
import io.futakotome.sec.dto.MenuDto;
import io.futakotome.sec.service.MenuService;
import io.futakotome.sec.mapper.MenuDtoMapper;
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
    @Transactional(rollbackFor = Exception.class)
    public boolean addMenu(AddMenuRequest addMenuRequest) {

        return false;
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




