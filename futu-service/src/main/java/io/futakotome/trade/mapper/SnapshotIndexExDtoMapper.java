package io.futakotome.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.trade.dto.SnapshotIndexExDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @author 86131
 * @description 针对表【t_snapshot_index_ex】的数据库操作Mapper
 * @createDate 2025-02-20 09:36:01
 * @Entity io.futakotome.trade.dto.SnapshotIndexExDto
 */
@Mapper
public interface SnapshotIndexExDtoMapper extends BaseMapper<SnapshotIndexExDto> {
    int insertBatch(@Param("snapshotIndexExDtoCollection") Collection<SnapshotIndexExDto> snapshotIndexExDtoCollection);
}




