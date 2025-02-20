package io.futakotome.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.futakotome.trade.dto.OrderDto;
import org.apache.ibatis.annotations.Mapper;

/**
* @author pc
* @description 针对表【t_order】的数据库操作Mapper
* @createDate 2023-05-19 15:03:38
* @Entity io.futakotome.trade.dto.OrderDto
*/
public interface OrderDtoMapper extends BaseMapper<OrderDto> {
    int insertSelective(OrderDto orderDto);
}




