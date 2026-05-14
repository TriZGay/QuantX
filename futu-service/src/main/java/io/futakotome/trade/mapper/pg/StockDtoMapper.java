package io.futakotome.trade.mapper.pg;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.futakotome.trade.dto.PlateDto;
import io.futakotome.trade.dto.StockDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;
import java.util.List;

@Mapper
public interface StockDtoMapper extends BaseMapper<StockDto> {
    int insertBatch(@Param("stockDtoCollection") Collection<StockDto> stockDtoCollection);

    StockDto searchOneByCode(@Param("code") String code);

    @Select("select s.id as id,name,market,code,lot_size,stock_type,listing_date,delisting,exchange_type " +
            "from t_stock s inner join t_plate_stock ts on s. id = ts.stock_id " +
            "where ts.plate_id = #{plateId}"
    )
    List<StockDto> searchStocksByPlateId(@Param("plateId") Long plateId);
}




