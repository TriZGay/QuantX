package io.futakotome.sec.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.sec.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author pc
 * @description 针对表【t_user】的数据库操作Mapper
 * @createDate 2023-11-08 14:34:25
 * @Entity io.futakotome.sec.dto.UserDto
 */
@Mapper
public interface UserDtoMapper extends BaseMapper<UserDto> {
    int insertAll(UserDto userDto);

    int updateSelective(UserDto userDto);
}




