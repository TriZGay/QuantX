package io.futakotome.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.trade.dto.IpoCnDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

@Mapper
public interface IpoCnDtoMapper extends BaseMapper<IpoCnDto> {
    int insertBatch(@Param("ipoCnDtoCollection") Collection<IpoCnDto> ipoCnDtoCollection);

    List<IpoCnDto> findAll();
}




