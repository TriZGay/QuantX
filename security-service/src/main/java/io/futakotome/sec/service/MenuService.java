package io.futakotome.sec.service;

import io.futakotome.sec.controller.vo.AddMenuRequest;
import io.futakotome.sec.dto.MenuDto;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author pc
 * @description 针对表【t_menu】的数据库操作Service
 * @createDate 2023-11-08 14:36:09
 */
public interface MenuService extends IService<MenuDto> {

    boolean addMenu(AddMenuRequest addMenuRequest);

    boolean deleteBy(Long id);
}
