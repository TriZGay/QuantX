package io.futakotome.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.futakotome.trade.dto.OrderDto;
import io.futakotome.trade.dto.message.OrderPushContent;
import io.futakotome.trade.dto.message.PlaceOrderContent;

/**
 * @author pc
 * @description 针对表【t_order】的数据库操作Service
 * @createDate 2023-05-19 15:03:38
 */
public interface OrderDtoService extends IService<OrderDto> {
    void placeOrder(PlaceOrderContent result);

    int saveOrderBatch(OrderPushContent orderPush);
}
