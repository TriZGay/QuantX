package io.futakotome.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.trade.dto.SnapshotTrustExDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @author 86131
 * @description 针对表【t_snapshot_trust_ex】的数据库操作Mapper
 * @createDate 2025-02-20 09:36:01
 * @Entity io.futakotome.trade.dto.SnapshotTrustExDto
 */
@Mapper
public interface SnapshotTrustExDtoMapper extends BaseMapper<SnapshotTrustExDto> {
    int insertBatch(@Param("snapshotTrustExDtoCollection") Collection<SnapshotTrustExDto> snapshotTrustExDtoCollection);
}




