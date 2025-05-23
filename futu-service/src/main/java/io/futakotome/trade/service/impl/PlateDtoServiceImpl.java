package io.futakotome.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.trade.controller.vo.ListPlateRequest;
import io.futakotome.trade.controller.vo.ListPlateResponse;
import io.futakotome.trade.domain.code.MarketType;
import io.futakotome.trade.domain.code.PlateSetType;
import io.futakotome.trade.dto.PlateDto;
import io.futakotome.trade.dto.PlateStockDto;
import io.futakotome.trade.dto.StockDto;
import io.futakotome.trade.mapper.PlateDtoMapper;
import io.futakotome.trade.mapper.PlateStockDtoMapper;
import io.futakotome.trade.service.PlateDtoService;
import io.futakotome.trade.service.PlateStockDtoService;
import io.futakotome.trade.service.StockDtoService;
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
    private static final ReentrantLock lock = new ReentrantLock();
    private final StockDtoService stockService;
    private final PlateStockDtoService plateStockService;

    public PlateDtoServiceImpl(StockDtoService stockService, PlateStockDtoService plateStockService) {
        this.stockService = stockService;
        this.plateStockService = plateStockService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBatch(List<PlateDto> newPlates) {
        lock.lock();
        try {
            List<PlateDto> allPlates = list();
            newPlates.removeIf(allPlates::contains);
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
    @Transactional(rollbackFor = Exception.class)
    public int insertBatch(StockDto stockDto, List<PlateDto> toInsertPlates) {
        lock.lock();
        try {
            StockDto oneStock = stockService.getOne(Wrappers.query(stockDto)
                    .eq("market", stockDto.getMarket())
                    .eq("code", stockDto.getCode()));
            if (Objects.isNull(oneStock)) {
                return 0;
            }
            List<PlateDto> existPlates = new ArrayList<>();
            List<PlateDto> newPlates = new ArrayList<>();
            for (PlateDto plateDto : toInsertPlates) {
                PlateDto isExistPlate = getOne(Wrappers.query(plateDto)
                        .eq("market", plateDto.getMarket())
                        .eq("code", plateDto.getCode()));
                if (Objects.nonNull(isExistPlate)) {
                    existPlates.add(isExistPlate);
                } else {
                    newPlates.add(plateDto);
                }
            }
            int totalInsertRow = 0;
            if (!newPlates.isEmpty()) {
                int insertRow = getBaseMapper().insertBatch(newPlates);
                List<PlateStockDto> relations = newPlates.stream()
                        .map(plateDto -> {
                            PlateStockDto plateStockDto = new PlateStockDto();
                            plateStockDto.setPlateId(plateDto.getId());
                            plateStockDto.setStockId(oneStock.getId());
                            return plateStockDto;
                        }).collect(Collectors.toList());
                PlateStockDtoMapper plateStockDtoMapper = (PlateStockDtoMapper) plateStockService.getBaseMapper();
                if (plateStockDtoMapper.insertBatch(relations) > 0) {
                    totalInsertRow += insertRow;
                }
            }
            if (!existPlates.isEmpty()) {
                PlateStockDtoMapper plateStockDtoMapper = (PlateStockDtoMapper) plateStockService.getBaseMapper();
                List<PlateStockDto> relations = new ArrayList<>();
                for (PlateDto existPlateDto : existPlates) {
                    boolean relationExist = plateStockDtoMapper.exists(Wrappers.query(new PlateStockDto())
                            .eq("plate_id", existPlateDto.getId())
                            .eq("stock_id", oneStock.getId()));
                    if (!relationExist) {
                        PlateStockDto relation = new PlateStockDto();
                        relation.setPlateId(existPlateDto.getId());
                        relation.setStockId(oneStock.getId());
                        relations.add(relation);
                    }
                }
                if (!relations.isEmpty()) {
                    if (plateStockDtoMapper.insertBatch(relations) > 0) {
                        totalInsertRow += relations.size();
                    }
                }
            }
            return totalInsertRow;
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
            response.setMarket(MarketType.getNameByCode(plateDto.getMarket()));
            response.setMarketCode(plateDto.getMarket());
            response.setPlateType(PlateSetType.getNameByCode(plateDto.getPlateType()));
            response.setPlateTypeCode(plateDto.getPlateType());
            return response;
        });
    }
}




