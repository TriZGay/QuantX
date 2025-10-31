package io.futakotome.trade.mapper.ck;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.trade.dto.KLineWeekArcDto;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
* @author 86131
* @description 针对表【t_kl_week_arc】的数据库操作Mapper
* @createDate 2025-10-31 15:39:43
* @Entity io.futakotome.trade.dto.KLineWeekArcDto
*/
public interface KLineWeekArcDtoMapper extends BaseMapper<KLineWeekArcDto> {
    int insertBatch(@Param("kLineWeekArcDtoCollection") Collection<KLineWeekArcDto> kLineWeekArcDtoCollection);
}




