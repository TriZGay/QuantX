package io.futakotome.stock.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Collection;

import io.futakotome.stock.dto.IpoHkDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IpoHkDtoMapper extends BaseMapper<IpoHkDto> {
    int insertBatch(@Param("ipoHkDtoCollection") Collection<IpoHkDto> ipoHkDtoCollection);
}




