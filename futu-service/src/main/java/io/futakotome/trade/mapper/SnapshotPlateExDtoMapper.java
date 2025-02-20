package io.futakotome.trade.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Collection;

import io.futakotome.trade.dto.SnapshotPlateExDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 86131
* @description 针对表【t_snapshot_plate_ex】的数据库操作Mapper
* @createDate 2025-02-20 09:36:01
* @Entity io.futakotome.trade.dto.SnapshotPlateExDto
*/
public interface SnapshotPlateExDtoMapper extends BaseMapper<SnapshotPlateExDto> {
    int insertBatch(@Param("snapshotPlateExDtoCollection") Collection<SnapshotPlateExDto> snapshotPlateExDtoCollection);
}




