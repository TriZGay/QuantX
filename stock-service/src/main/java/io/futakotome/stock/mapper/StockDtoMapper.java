package io.futakotome.stock.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Collection;

import io.futakotome.stock.dto.StockDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StockDtoMapper extends BaseMapper<StockDto> {
    int insertBatch(@Param("stockDtoCollection") Collection<StockDto> stockDtoCollection);
}




