package io.futakotome.trade.mapper.ck;
import org.apache.ibatis.annotations.Param;
import java.util.Collection;

import io.futakotome.trade.dto.KLineMin1ArcDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 86131
* @description 针对表【t_kl_min_1_arc】的数据库操作Mapper
* @createDate 2025-10-12 13:49:19
* @Entity io.futakotome.trade.dto.KLineMin1Arc
*/
public interface KLineMin1ArcMapper extends BaseMapper<KLineMin1ArcDto> {
    int insertBatch(@Param("kLineMin1ArcDtoCollection") Collection<KLineMin1ArcDto> kLineMin1ArcDtoCollection);
}




