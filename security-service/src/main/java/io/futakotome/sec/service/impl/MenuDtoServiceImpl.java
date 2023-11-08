package io.futakotome.sec.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.sec.dto.MenuDto;
import io.futakotome.sec.service.MenuDtoService;
import io.futakotome.sec.mapper.MenuDtoMapper;
import org.springframework.stereotype.Service;

/**
* @author pc
* @description 针对表【t_menu】的数据库操作Service实现
* @createDate 2023-11-08 14:36:09
*/
@Service
public class MenuDtoServiceImpl extends ServiceImpl<MenuDtoMapper, MenuDto>
    implements MenuDtoService{

}




