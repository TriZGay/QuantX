package io.futakotome.sec.mapper;
import org.apache.ibatis.annotations.Param;

import io.futakotome.sec.dto.MenuDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author pc
 * @description 针对表【t_menu】的数据库操作Mapper
 * @createDate 2023-11-08 14:36:09
 * @Entity io.futakotome.sec.dto.MenuDto
 */
@Mapper
public interface MenuDtoMapper extends BaseMapper<MenuDto> {
    int insertAll(MenuDto menuDto);

    int updateSelective(MenuDto menuDto);
}




