package io.futakotome.trade.mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.trade.dto.CapitalDistributionDto;
import org.apache.ibatis.annotations.Mapper;

/**
* @author pc
* @description 针对表【t_capital_distribution】的数据库操作Mapper
* @createDate 2023-09-12 15:37:33
* @Entity io.futakotome.trade.dto.CapitalDistributionDto
*/
@Mapper
public interface CapitalDistributionDtoMapper extends BaseMapper<CapitalDistributionDto> {
    int insertSelective(CapitalDistributionDto capitalDistributionDto);
}




