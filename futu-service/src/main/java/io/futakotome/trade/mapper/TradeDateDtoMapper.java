package io.futakotome.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.trade.dto.TradeDateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

public interface TradeDateDtoMapper extends BaseMapper<TradeDateDto> {
    int insertBatch(@Param("tradeDateDtoCollection") Collection<TradeDateDto> tradeDateDtoCollection);
}




