package io.futakotome.trade.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.trade.dto.AccSubDto;
import io.futakotome.trade.dto.OrderDto;
import io.futakotome.trade.dto.message.OrderContent;
import io.futakotome.trade.dto.message.OrderPushContent;
import io.futakotome.trade.dto.message.PlaceOrderContent;
import io.futakotome.trade.mapper.OrderDtoMapper;
import io.futakotome.trade.service.AccSubDtoService;
import io.futakotome.trade.service.OrderDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveOrderBatch(OrderPushContent orderPush) {
        lock.lock();
        try {
            if (Objects.nonNull(orderPush.getOrderList())) {
                if (!orderPush.getOrderList().isEmpty()) {
                    List<OrderDto> orderDtoList = orderPush.getOrderList()
                            .stream().map(orderVo -> this.messageVo2Dto(orderPush, orderVo))
                            .collect(Collectors.toList());
                    return getBaseMapper().insertBatch(orderDtoList);
                }
            }
            return 0;
        } catch (Exception e) {
            LOGGER.error("账号:{}插入订单失败", orderPush.getHeader().getAccID(), e);
            throw new RuntimeException("账号:" + orderPush.getHeader().getAccID() + "插入订单失败", e);
        } finally {
            lock.unlock();
        }
    }

    private OrderDto messageVo2Dto(OrderPushContent vo, OrderContent orderVo) {
        OrderDto orderDto = new OrderDto();
        orderDto.setAccId(vo.getHeader().getAccID());
        orderDto.setTradeEnv(vo.getHeader().getTrdEnv());
        orderDto.setAccTradeMarket(vo.getHeader().getTrdMarket());
        orderDto.setTradeSide(orderVo.getTrdSide());
        orderDto.setOrderType(orderVo.getOrderType());
        orderDto.setOrderStatus(orderVo.getOrderStatus());
        orderDto.setOrderId(orderVo.getOrderID());
        orderDto.setOrderIdEx(orderVo.getOrderIDEx());
        orderDto.setCode(orderVo.getCode());
        orderDto.setName(orderVo.getName());
        orderDto.setQty(orderVo.getQty());
        orderDto.setPrice(orderVo.getPrice());
        orderDto.setCreateTime(LocalDateTime.parse(orderVo.getCreateTime(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        orderDto.setUpdateTime(LocalDateTime.parse(orderVo.getUpdateTime(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        orderDto.setFillQty(orderVo.getFillQty());
        orderDto.setFillAvgPrice(orderVo.getFillAvgPrice());
        orderDto.setLastErrMsg(orderVo.getLastErrMsg());
        orderDto.setSecurityMarket(orderVo.getSecMarket());
        orderDto.setRemark(orderVo.getRemark());
        orderDto.setAuxPrice(orderVo.getAuxPrice());
        orderDto.setTrailType(orderVo.getTrailType());
        orderDto.setTrailValue(orderVo.getTrailValue());
        orderDto.setTrailSpread(orderVo.getTrailSpread());
        orderDto.setCurrency(orderVo.getCurrency());
        orderDto.setTradeMarket(orderVo.getTrdMarket());
        return orderDto;
    }
}




