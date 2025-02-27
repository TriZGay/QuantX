package io.futakotome.trade.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

import io.futakotome.trade.dto.AccSubDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author 86131
 * @description 针对表【t_acc_sub】的数据库操作Mapper
 * @createDate 2025-02-26 10:02:50
 * @Entity io.futakotome.trade.dto.AccSubDto
 */
@Mapper
public interface AccSubDtoMapper extends BaseMapper<AccSubDto> {
    int insertBatch(@Param("accSubDtoCollection") Collection<AccSubDto> accSubDtoCollection);
}




