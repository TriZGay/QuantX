package io.futakotome.stock.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Collection;

import io.futakotome.stock.dto.IpoCnDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IpoCnDtoMapper extends BaseMapper<IpoCnDto> {
    int insertBatch(@Param("ipoCnDtoCollection") Collection<IpoCnDto> ipoCnDtoCollection);


}




