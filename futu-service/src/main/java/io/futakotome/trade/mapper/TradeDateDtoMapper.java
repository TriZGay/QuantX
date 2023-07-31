package io.futakotome.trade.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Collection;

import io.futakotome.trade.dto.TradeDateDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TradeDateDtoMapper extends BaseMapper<TradeDateDto> {
    int insertBatch(@Param("tradeDateDtoCollection") Collection<TradeDateDto> tradeDateDtoCollection);
}




