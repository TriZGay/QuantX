package io.futakotome.trade.mapper.pg;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.trade.dto.PlateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
@Mapper
public interface PlateDtoMapper extends BaseMapper<PlateDto> {
    int insertBatch(@Param("plateDtoCollection") Collection<PlateDto> plateDtoCollection);

    List<PlateDto> searchByMarketEquals(@Param("market") Integer market);

    List<PlateDto> searchALLByStockId(@Param("stockId") Long stockId);
}




