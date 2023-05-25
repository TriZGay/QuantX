package io.futakotome.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.trade.dto.IpoHkDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

@Mapper
public interface IpoHkDtoMapper extends BaseMapper<IpoHkDto> {
    int insertBatch(@Param("ipoHkDtoCollection") Collection<IpoHkDto> ipoHkDtoCollection);
}




