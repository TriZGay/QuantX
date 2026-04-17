package io.futakotome.trade.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.PlateDto;
import io.futakotome.trade.dto.PlateStockDto;
import io.futakotome.trade.dto.StockDto;
import io.futakotome.trade.dto.message.CommonStaticInfo;
import io.futakotome.trade.dto.message.StockContent;
import io.futakotome.trade.event.StaticInfoUpdateEvent;
import io.futakotome.trade.event.StockInPlateUpdateEvent;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
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
    public void onStaticInfoUpdate(StaticInfoUpdateEvent event) {
        CommonStaticInfo staticInfo = event.getStaticInfo();
        List<StockDto> stockDtos = stockContent2StockDto(event.getStockContents());
        if (staticInfo.getStockType().equals(7)) {
            //标的物类型为板块 需要在板块表也入库 用于后续建立关联关系
            List<PlateDto> plateDtos = stockDtos.stream().map(s -> {
                PlateDto plateDto = new PlateDto();
                plateDto.setName(s.getName());
                plateDto.setCode(s.getCode());
                plateDto.setMarket(s.getMarket());
                return plateDto;
            }).collect(Collectors.toList());
            int insertPlateRow = plateDtoService.insertBatch(staticInfo.getMarket(), plateDtos);
            wsService.sendNotify("插入板块:" + insertPlateRow + "行");
        }
        int insertRow = stockDtoService.insertBatch(staticInfo.getMarket(), staticInfo.getStockType(), stockDtos);
        wsService.sendNotify("插入静态标的物:" + insertRow + "行");
    }

    @EventListener
    @Transactional(rollbackFor = Exception.class)
    public void onStockInPlateUpdate(StockInPlateUpdateEvent event) {
        List<StockDto> stockDtos = stockContent2StockDto(event.getStockContents());
        if (stockDtos.isEmpty()) {
            LOGGER.warn("板块:{}下没有标的物.", event.getPlateDto().getCode());
            return;
        }
        PlateDto foundPlate = plateDtoService.getOne(Wrappers.query(event.getPlateDto()).eq("code", event.getPlateDto().getCode()));
        boolean hasPlate = plateStockDtoService.getBaseMapper().exists(Wrappers.query(new PlateStockDto()).eq("plate_id", foundPlate.getId()));
        if (hasPlate) {
            //关联表有板块
            LOGGER.info("关系表中有板块信息");
            List<String> codes = stockDtos.stream().map(StockDto::getCode).collect(Collectors.toList());
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
            List<String> codes = stockDtos.stream().map(StockDto::getCode).collect(Collectors.toList());
            List<StockDto> foundStocks = stockDtoService.list(Wrappers.query(new StockDto()).in("code", codes));
            List<PlateStockDto> relationsToInsert = foundStocks.stream().map(s -> new PlateStockDto(foundPlate.getId(), s.getId())).collect(Collectors.toList());
            int insertRow = plateStockDtoService.insertBatch(relationsToInsert);
            String notify = "关系表中无板块:" + foundPlate.getCode() + "信息,插入关系数据:" + insertRow + "行";
            LOGGER.info(notify);
            wsService.sendNotify(notify);
        }

    }

    private List<StockDto> stockContent2StockDto(List<StockContent> stockContents) {
        return stockContents.stream().map(vo -> {
            StockDto dto = new StockDto();
            dto.setName(vo.getBasic().getName());
            dto.setCode(vo.getBasic().getSecurity().getCode());
            dto.setLotSize(vo.getBasic().getLotSize());
            dto.setStockType(vo.getBasic().getSecType());
            dto.setMarket(vo.getBasic().getSecurity().getMarket());
            dto.setListingDate(LocalDate.parse(vo.getBasic().getListTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            dto.setDelisting(vo.getBasic().getDelisting() ? 1 : 0);
            dto.setExchangeType(vo.getBasic().getExchType());
            dto.setStockId(vo.getBasic().getId());
            if (Objects.nonNull(vo.getWarrantExData())) {
                dto.setStockChildType(vo.getWarrantExData().getType());
                dto.setStockOwner(vo.getWarrantExData().getOwner().getCode());
            }
            if (Objects.nonNull(vo.getOptionExData())) {
                dto.setOptionType(vo.getOptionExData().getType());
                dto.setStrikeTime(vo.getOptionExData().getStrikeTime());
                dto.setStrikePrice(vo.getOptionExData().getStrikePrice());
                dto.setOptionMarket(vo.getOptionExData().getMarket());
                dto.setSuspension(vo.getOptionExData().getSuspend());
                dto.setIndexOptionType(vo.getOptionExData().getIndexOptionType());
            }
            if (Objects.nonNull(vo.getFutureExData())) {
                dto.setMainContract(vo.getFutureExData().getMainContract() ? 1 : 0);
                dto.setLastTradeTime(LocalDate.parse(vo.getFutureExData().getLastTradeTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            return dto;
        }).collect(Collectors.toList());
    }
}
