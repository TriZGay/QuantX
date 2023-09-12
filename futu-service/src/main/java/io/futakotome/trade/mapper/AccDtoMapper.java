package io.futakotome.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.trade.dto.AccDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @author pc
 * @description 针对表【t_acc】的数据库操作Mapper
 * @createDate 2023-05-09 16:34:10
 * @Entity io.futakotome.trade.dto.AccDto
 */
@Mapper
public interface AccDtoMapper extends BaseMapper<AccDto> {
    int insertBatch(@Param("accDtoCollection") Collection<AccDto> accDtoCollection);

    int updateBatch(@Param("accDtoCollection") Collection<AccDto> accDtoCollection);
}




