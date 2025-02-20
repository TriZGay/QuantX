package io.futakotome.trade.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Collection;

import io.futakotome.trade.dto.SnapshotIndexExDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 86131
* @description 针对表【t_snapshot_index_ex】的数据库操作Mapper
* @createDate 2025-02-20 09:36:01
* @Entity io.futakotome.trade.dto.SnapshotIndexExDto
*/
public interface SnapshotIndexExDtoMapper extends BaseMapper<SnapshotIndexExDto> {
    int insertBatch(@Param("snapshotIndexExDtoCollection") Collection<SnapshotIndexExDto> snapshotIndexExDtoCollection);
}




