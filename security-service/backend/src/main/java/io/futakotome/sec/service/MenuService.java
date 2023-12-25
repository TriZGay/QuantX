package io.futakotome.sec.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.futakotome.sec.controller.vo.AddMenuRequest;
import io.futakotome.sec.controller.vo.ListMenuRequest;
import io.futakotome.sec.controller.vo.UpdateMenuRequest;
import io.futakotome.sec.domain.Menu;
import io.futakotome.sec.dto.MenuDto;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author pc
 * @description 针对表【t_menu】的数据库操作Service
 * @createDate 2023-11-08 14:36:09
 */
public interface MenuService extends IService<MenuDto> {
    IPage<Menu> queryMenus(ListMenuRequest listMenuRequest);

    boolean updateMenuById(Long id, UpdateMenuRequest updateMenuRequest);

    boolean addMenu(AddMenuRequest addMenuRequest);

    boolean deleteBy(Long id);
}
