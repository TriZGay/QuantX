package io.futakotome.trade.mapper.ck;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.trade.dto.KLineQuarterArcDto;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @author 86131
 * @description 针对表【t_kl_quarter_arc】的数据库操作Mapper
 * @createDate 2025-10-31 15:07:34
 * @Entity io.futakotome.trade.dto.KLineQuarterArcDto
 */
public interface KLineQuarterArcDtoMapper extends BaseMapper<KLineQuarterArcDto> {
    int insertBatch(@Param("kLineQuarterArcDtoCollection") Collection<KLineQuarterArcDto> kLineQuarterArcDtoCollection);
}




