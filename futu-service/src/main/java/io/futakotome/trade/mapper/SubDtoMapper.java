package io.futakotome.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.trade.dto.SubDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @author pc
 * @description 针对表【t_sub】的数据库操作Mapper
 * @createDate 2023-05-08 15:00:30
 * @Entity io.futakotome.sub.dto.SubDto
 */
@Mapper
public interface SubDtoMapper extends BaseMapper<SubDto> {
    int insertBatch(@Param("subDtoCollection") Collection<SubDto> subDtoCollection);

    int deleteBySecurityCodeAndSubType(@Param("securityCode") String securityCode, @Param("subType") Integer subType);
}




