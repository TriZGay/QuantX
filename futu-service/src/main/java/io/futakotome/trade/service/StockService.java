package io.futakotome.trade.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.PlateDto;
import io.futakotome.trade.dto.PlateStockDto;
import io.futakotome.trade.dto.StockDto;
import io.futakotome.trade.event.StockInPlateUpdateEvent;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);
    private final PlateDtoService plateDtoService;
    private final PlateStockDtoService plateStockDtoService;
    private final StockDtoService stockDtoService;
    private final QuantxFutuWsService wsService;

    public StockService(PlateDtoService plateDtoService, PlateStockDtoService plateStockDtoService, StockDtoService stockDtoService, QuantxFutuWsService wsService) {
        this.plateDtoService = plateDtoService;
        this.plateStockDtoService = plateStockDtoService;
        this.stockDtoService = stockDtoService;
        this.wsService = wsService;
    }

    @EventListener
    @Transactional(rollbackFor = Exception.class)
    public void onStockInPlateUpdate(StockInPlateUpdateEvent event) {
        PlateDto foundPlate = plateDtoService.getOne(Wrappers.query(event.getPlateDto()).eq("code", event.getPlateDto().getCode()));
        boolean hasPlate = plateStockDtoService.getBaseMapper().exists(Wrappers.query(new PlateStockDto()).eq("plate_id", foundPlate.getId()));
        if (hasPlate) {
            //关联表有板块
            LOGGER.info("关系表中有板块信息");
            List<String> codes = event.getStockDtos().stream().map(StockDto::getCode).collect(Collectors.toList());
            List<Long> foundStockIds = stockDtoService.list(Wrappers.query(new StockDto()).in("code", codes))
                    .stream().map(StockDto::getId).collect(Collectors.toList());
            List<Long> foundStockIdsInRelations = plateStockDtoService.list(Wrappers.query(new PlateStockDto()).eq("plate_id", foundPlate.getId()))
                    .stream().map(PlateStockDto::getStockId).collect(Collectors.toList());
            //求差集把新加的筛出来
            Collection<Long> newStockIds = CollectionUtils.subtract(foundStockIds, foundStockIdsInRelations);
            if (!newStockIds.isEmpty()) {
                List<PlateStockDto> toInsertRelations = newStockIds.stream().map(newStockId -> new PlateStockDto(foundPlate.getId(), newStockId)).collect(Collectors.toList());
                int insertRow = plateStockDtoService.insertBatch(toInsertRelations);
                String notify = "板块:" + foundPlate.getCode() + "新增" + insertRow + "个成分股";
                LOGGER.info(notify);
                wsService.sendNotify(notify);
            } else {
                String notify = "板块:" + foundPlate.getCode() + "没有新增成分股";
                LOGGER.info(notify);
                wsService.sendNotify(notify);
            }
        } else {
            //关联表无板块
            LOGGER.info("关系表中无板块信息.");
            List<String> codes = event.getStockDtos().stream().map(StockDto::getCode).collect(Collectors.toList());
            List<StockDto> foundStocks = stockDtoService.list(Wrappers.query(new StockDto()).in("code", codes));
            List<PlateStockDto> relationsToInsert = foundStocks.stream().map(s -> new PlateStockDto(foundPlate.getId(), s.getId())).collect(Collectors.toList());
            int insertRow = plateStockDtoService.insertBatch(relationsToInsert);
            String notify = "关系表中无板块:" + foundPlate.getCode() + "信息,插入关系数据:" + insertRow + "行";
            LOGGER.info(notify);
            wsService.sendNotify(notify);
        }

    }
}
