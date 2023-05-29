package io.futakotome.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.trade.dto.AccInfoDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author pc
 * @description 针对表【t_acc_info】的数据库操作Mapper
 * @createDate 2023-05-11 11:06:44
 * @Entity io.futakotome.trade.dto.AccInfoDto
 */
@Mapper
public interface AccInfoDtoMapper extends BaseMapper<AccInfoDto> {
    int insertSelective(AccInfoDto accInfoDto);
}




