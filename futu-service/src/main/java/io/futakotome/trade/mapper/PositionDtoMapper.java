package io.futakotome.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.trade.dto.PositionDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author pc
 * @description 针对表【t_position】的数据库操作Mapper
 * @createDate 2023-05-18 17:29:48
 * @Entity io.futakotome.trade.dto.PositionDto
 */
public interface PositionDtoMapper extends BaseMapper<PositionDto> {
    int insertSelective(PositionDto positionDto);
}




