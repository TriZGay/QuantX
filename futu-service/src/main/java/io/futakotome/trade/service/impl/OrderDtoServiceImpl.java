package io.futakotome.trade.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.trade.dto.AccSubDto;
import io.futakotome.trade.dto.OrderDto;
import io.futakotome.trade.dto.message.PlaceOrderContent;
import io.futakotome.trade.mapper.OrderDtoMapper;
import io.futakotome.trade.service.AccSubDtoService;
import io.futakotome.trade.service.OrderDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author pc
 * @description 针对表【t_order】的数据库操作Service实现
 * @createDate 2023-05-19 15:03:38
 */
@Service
public class OrderDtoServiceImpl extends ServiceImpl<OrderDtoMapper, OrderDto>
        implements OrderDtoService {
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDtoServiceImpl.class);
    private final AccSubDtoService subDtoService;

    public OrderDtoServiceImpl(AccSubDtoService subDtoService) {
        this.subDtoService = subDtoService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void placeOrder(PlaceOrderContent result) {
        lock.lock();
        try {
            AccSubDto subRecord = subDtoService.getOne(Wrappers.query(new AccSubDto())
                    .eq("acc_id", result.getHeader().getAccID()));
            if (Objects.isNull(subRecord)) {
                //已经订阅了
                AccSubDto newSub = new AccSubDto();
                newSub.setAccId(result.getHeader().getAccID());
                int subInsertRow = subDtoService.insertBatch(Arrays.asList(newSub));
                LOGGER.info("订阅账号:{}入库{}条", result.getHeader().getAccID(), subInsertRow);
            }
            OrderDto orderDto = new OrderDto();
            orderDto.setAccId(result.getHeader().getAccID());
            orderDto.setTradeEnv(result.getHeader().getTrdEnv());
            orderDto.setAccTradeMarket(result.getHeader().getTrdMarket());
            orderDto.setOrderId(result.getOrderID());
            int insertRow = getBaseMapper().insertSelective(orderDto);
            LOGGER.info("单号:{}入库{}条", result.getOrderID(), insertRow);
        } catch (Exception e) {
            LOGGER.error("单号:{},处理失败", result.getOrderID(), e);
        } finally {
            lock.unlock();
        }
    }
}




