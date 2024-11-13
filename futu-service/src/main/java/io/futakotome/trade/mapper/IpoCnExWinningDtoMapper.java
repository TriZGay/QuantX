package io.futakotome.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.trade.dto.IpoCnExWinningDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

@Mapper
public interface IpoCnExWinningDtoMapper extends BaseMapper<IpoCnExWinningDto> {
    int insertBatch(@Param("ipoCnExWinningDtoCollection") Collection<IpoCnExWinningDto> ipoCnExWinningDtoCollection);
}




