package io.futakotome.stock.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Collection;

import io.futakotome.stock.dto.IpoCnExWinningDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IpoCnExWinningDtoMapper extends BaseMapper<IpoCnExWinningDto> {
    int insertBatch(@Param("ipoCnExWinningDtoCollection") Collection<IpoCnExWinningDto> ipoCnExWinningDtoCollection);
}




