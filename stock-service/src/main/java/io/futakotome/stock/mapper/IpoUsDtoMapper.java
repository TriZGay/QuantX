package io.futakotome.stock.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Collection;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.stock.dto.IpoUsDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IpoUsDtoMapper extends BaseMapper<IpoUsDto> {
    int insertBatch(@Param("ipoUsDtoCollection") Collection<IpoUsDto> ipoUsDtoCollection);
}




