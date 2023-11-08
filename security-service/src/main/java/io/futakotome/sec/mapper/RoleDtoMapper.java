package io.futakotome.sec.mapper;

import io.futakotome.sec.dto.RoleDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author pc
 * @description 针对表【t_role】的数据库操作Mapper
 * @createDate 2023-11-08 14:30:40
 * @Entity io.futakotome.sec.dto.RoleDto
 */
@Mapper
public interface RoleDtoMapper extends BaseMapper<RoleDto> {

}




