package io.futakotome.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.trade.dto.PlateStockDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

@Mapper
public interface PlateStockDtoMapper extends BaseMapper<PlateStockDto> {
    int insertBatch(@Param("plateStockDtoCollection") Collection<PlateStockDto> plateStockDtoCollection);

}




