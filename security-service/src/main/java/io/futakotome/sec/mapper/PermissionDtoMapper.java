package io.futakotome.sec.mapper;
import org.apache.ibatis.annotations.Param;

import io.futakotome.sec.dto.PermissionDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author pc
 * @description 针对表【t_permission】的数据库操作Mapper
 * @createDate 2023-11-08 14:36:36
 * @Entity io.futakotome.sec.dto.PermissionDto
 */
@Mapper
public interface PermissionDtoMapper extends BaseMapper<PermissionDto> {
    int insertAll(PermissionDto permissionDto);

    int updateSelective(PermissionDto permissionDto);
}




