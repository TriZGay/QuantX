package io.futakotome.trade.mapper.ck;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.trade.dto.KLineMonthArcDto;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @author 86131
 * @description 针对表【t_kl_month_arc】的数据库操作Mapper
 * @createDate 2025-10-30 14:57:36
 * @Entity io.futakotome.trade.dto.KLineMonthArcDto
 */
public interface KLineMonthArcDtoMapper extends BaseMapper<KLineMonthArcDto> {
    int insertBatch(@Param("kLineMonthArcDtoCollection") Collection<KLineMonthArcDto> kLineMonthArcDtoCollection);
}




