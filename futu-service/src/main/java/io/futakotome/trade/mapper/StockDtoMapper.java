package io.futakotome.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.trade.dto.StockDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

public interface StockDtoMapper extends BaseMapper<StockDto> {
    int insertBatch(@Param("stockDtoCollection") Collection<StockDto> stockDtoCollection);

    StockDto searchOneByCode(@Param("code") String code);
}




