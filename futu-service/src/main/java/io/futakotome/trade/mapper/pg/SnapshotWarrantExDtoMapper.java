package io.futakotome.trade.mapper.pg;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.trade.dto.SnapshotWarrantExDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @author 86131
 * @description 针对表【t_snapshot_warrant_ex】的数据库操作Mapper
 * @createDate 2025-02-20 09:36:01
 * @Entity io.futakotome.trade.dto.SnapshotWarrantExDto
 */
@Mapper
public interface SnapshotWarrantExDtoMapper extends BaseMapper<SnapshotWarrantExDto> {
    int insertBatch(@Param("snapshotWarrantExDtoCollection") Collection<SnapshotWarrantExDto> snapshotWarrantExDtoCollection);
}




