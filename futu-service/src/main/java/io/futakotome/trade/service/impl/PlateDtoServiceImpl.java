package io.futakotome.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.trade.controller.vo.ListPlateRequest;
import io.futakotome.trade.controller.vo.ListPlateResponse;
import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.controller.ws.QuantxWsController;
import io.futakotome.trade.domain.code.MarketType;
import io.futakotome.trade.domain.code.PlateSetType;
import io.futakotome.trade.dto.PlateDto;
import io.futakotome.trade.dto.PlateStockDto;
import io.futakotome.trade.dto.StockDto;
import io.futakotome.trade.event.PlateSetUpdateEvent;
import io.futakotome.trade.mapper.pg.PlateDtoMapper;
import io.futakotome.trade.mapper.pg.PlateStockDtoMapper;
import io.futakotome.trade.service.PlateDtoService;
import io.futakotome.trade.service.PlateStockDtoService;
import io.futakotome.trade.service.StockDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @author pc
 * @description 针对表【t_plate】的数据库操作Service实现
 * @createDate 2023-04-11 10:12:42
 */
@Service
public class PlateDtoServiceImpl extends ServiceImpl<PlateDtoMapper, PlateDto>
        implements PlateDtoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlateDtoServiceImpl.class);
    private static final ReentrantLock lock = new ReentrantLock();
    private final StockDtoService stockDtoService;
    private final QuantxFutuWsService wsService;

    public PlateDtoServiceImpl(StockDtoService stockDtoService, QuantxFutuWsService wsService) {
        this.stockDtoService = stockDtoService;
        this.wsService = wsService;
    }

    @EventListener
    public void onPlateSetUpdate(PlateSetUpdateEvent event) {
        List<PlateDto> toInsertPlates = event.getPlateInfos().stream().map(plateVo -> {
            PlateDto plateDto = new PlateDto();
            plateDto.setName(plateVo.getName());
            plateDto.setCode(plateVo.getPlate().getCode());
            plateDto.setMarket(plateVo.getPlate().getMarket());
            return plateDto;
        }).collect(Collectors.toList());
        int insertRow = insertBatch(event.getMarket(), toInsertPlates);
        String str = "同步板块数据,插入条数:" + insertRow;
        LOGGER.info(str);
        wsService.sendNotify(str);
        //静态表也插一下,保持和‘同步静态数据’的接口数据一致
        List<StockDto> stockDtos = event.getPlateInfos().stream().map(p -> {
            StockDto stockDto = new StockDto();
            stockDto.setName(p.getName());
            stockDto.setMarket(p.getPlate().getMarket());
            stockDto.setCode(p.getPlate().getCode());
            stockDto.setStockType(7);
            return stockDto;
        }).collect(Collectors.toList());
        int insertStaticRow = stockDtoService.insertBatch(event.getMarket(), 7, stockDtos);
        String insertStaticLog = "插入静态标的物表,条数:" + insertStaticRow;
        LOGGER.info(insertStaticLog);
        wsService.sendNotify(insertStaticLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBatch(Integer market, List<PlateDto> newPlates) {
        lock.lock();
        try {
            List<PlateDto> allPlatesByMarket = list(Wrappers.lambdaQuery(new PlateDto()).eq(PlateDto::getMarket, market));
            newPlates.removeIf(allPlatesByMarket::contains);
            if (!newPlates.isEmpty()) {
                return getBaseMapper().insertBatch(newPlates);
            } else {
                return 0;
            }
        } catch (Exception e) {
            throw new RuntimeException("批量插入出错", e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public IPage<ListPlateResponse> page(ListPlateRequest request) {
        QueryWrapper<PlateDto> queryWrapper = Wrappers.query();
        Page<PlateDto> pagination = Page.of(1, 10);
        if (request.getName() != null) {
            queryWrapper.like("name", request.getName());
        }
        if (request.getMarket() != null) {
            queryWrapper.eq("market", request.getMarket());
        }
        if (request.getType() != null) {
            queryWrapper.eq("plate_type", request.getType());
        }
        if (request.getCurrent() != null) {
            pagination.setCurrent(request.getCurrent());
        }
        if (request.getSize() != null) {
            pagination.setSize(request.getSize());
        }
        return page(pagination, queryWrapper).convert(plateDto -> {
            ListPlateResponse response = new ListPlateResponse();
            response.setId(plateDto.getId());
            response.setName(plateDto.getName());
            response.setCode(plateDto.getCode());
            response.setMarket(MarketType.getName(plateDto.getMarket()));
            response.setMarketCode(plateDto.getMarket());
            response.setPlateType(PlateSetType.getNameByCode(plateDto.getPlateType()));
            response.setPlateTypeCode(plateDto.getPlateType());
            return response;
        });
    }
}




