package io.futakotome.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.stock.dto.PlateStockDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

@Mapper
public interface PlateStockDtoMapper extends BaseMapper<PlateStockDto> {
    int insertBatch(@Param("plateStockDtoCollection") Collection<PlateStockDto> plateStockDtoCollection);

}




