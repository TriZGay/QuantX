package io.futakotome.trade.mapper.ck;
import org.apache.ibatis.annotations.Param;
import java.util.Collection;

import io.futakotome.trade.dto.KLineDayArcDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 86131
* @description 针对表【t_kl_day_arc】的数据库操作Mapper
* @createDate 2025-10-30 10:56:58
* @Entity io.futakotome.trade.dto.KLineDayArcDto
*/
public interface KLineDayArcDtoMapper extends BaseMapper<KLineDayArcDto> {
    int insertBatch(@Param("kLineDayArcDtoCollection") Collection<KLineDayArcDto> kLineDayArcDtoCollection);
}




