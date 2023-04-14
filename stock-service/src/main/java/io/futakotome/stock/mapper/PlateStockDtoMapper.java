package io.futakotome.stock.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

import io.futakotome.stock.dto.PlateStockDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface PlateStockDtoMapper extends BaseMapper<PlateStockDto> {
    int insertBatch(@Param("plateStockDtoCollection") Collection<PlateStockDto> plateStockDtoCollection);
}




