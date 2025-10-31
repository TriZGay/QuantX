package io.futakotome.trade.mapper.ck;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.trade.dto.KLineYearArcDto;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
* @author 86131
* @description 针对表【t_kl_year_arc】的数据库操作Mapper
* @createDate 2025-10-31 17:04:05
* @Entity io.futakotome.trade.dto.KLineYearArcDto
*/
public interface KLineYearArcDtoMapper extends BaseMapper<KLineYearArcDto> {
    int insertBatch(@Param("kLineYearArcDtoCollection") Collection<KLineYearArcDto> kLineYearArcDtoCollection);
}




