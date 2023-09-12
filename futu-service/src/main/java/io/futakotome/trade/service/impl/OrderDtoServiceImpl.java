package io.futakotome.trade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.trade.dto.OrderDto;
import io.futakotome.trade.mapper.OrderDtoMapper;
import io.futakotome.trade.service.OrderDtoService;
import org.springframework.stereotype.Service;

/**
* @author pc
* @description 针对表【t_order】的数据库操作Service实现
* @createDate 2023-05-19 15:03:38
*/
@Service
public class OrderDtoServiceImpl extends ServiceImpl<OrderDtoMapper, OrderDto>
    implements OrderDtoService{

}




