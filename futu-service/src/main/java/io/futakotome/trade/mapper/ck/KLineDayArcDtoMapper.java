package io.futakotome.trade.mapper.ck;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.trade.dto.KLineDayArcDto;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
* @author 86131
* @description 针对表【t_kl_day_arc】的数据库操作Mapper
* @createDate 2025-10-30 10:56:58
* @Entity io.futakotome.trade.dto.KLineDayArcDto
*/
public interface KLineDayArcDtoMapper extends BaseMapper<KLineDayArcDto> {
    int insertBatch(@Param("kLineDayArcDtoCollection") Collection<KLineDayArcDto> kLineDayArcDtoCollection);
}




