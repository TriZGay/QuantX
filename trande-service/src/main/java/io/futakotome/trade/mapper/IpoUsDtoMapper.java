package io.futakotome.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.trade.dto.IpoUsDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

@Mapper
public interface IpoUsDtoMapper extends BaseMapper<IpoUsDto> {
    int insertBatch(@Param("ipoUsDtoCollection") Collection<IpoUsDto> ipoUsDtoCollection);
}




