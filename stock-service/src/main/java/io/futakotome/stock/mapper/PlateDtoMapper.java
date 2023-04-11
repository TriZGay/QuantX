package io.futakotome.stock.mapper;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;

import io.futakotome.stock.dto.PlateDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface PlateDtoMapper extends BaseMapper<PlateDto> {
    int insertBatch(@Param("plateDtoCollection") Collection<PlateDto> plateDtoCollection);
}




