package io.futakotome.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.trade.controller.vo.ListStockRequest;
import io.futakotome.trade.controller.vo.ListStockResponse;
import io.futakotome.trade.domain.code.DelistingType;
import io.futakotome.trade.domain.code.ExchangeType;
import io.futakotome.trade.domain.code.MarketType;
import io.futakotome.trade.domain.code.StockType;
import io.futakotome.trade.dto.StockDto;
import io.futakotome.trade.mapper.StockDtoMapper;
import io.futakotome.trade.service.StockDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author pc
 * @description 针对表【t_stock】的数据库操作Service实现
 * @createDate 2023-04-11 16:26:26
 */
@Service
public class StockDtoServiceImpl extends ServiceImpl<StockDtoMapper, StockDto>
        implements StockDtoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockDtoServiceImpl.class);
    private static final ReentrantLock lock = new ReentrantLock();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBatch(List<StockDto> toInsertStocks) {
        lock.lock();
        try {
            List<StockDto> allStocks = list();
            toInsertStocks.removeIf(allStocks::contains);
            if (!toInsertStocks.isEmpty()) {
                int totalInsertRow = 0;
                int insertLength = toInsertStocks.size();
                int i = 0;
                int batchLimit = 1000;
                while (insertLength > batchLimit) {
                    List<StockDto> batchInsertStocks = toInsertStocks.subList(i, i + batchLimit);
                    int insertRow = getBaseMapper().insertBatch(batchInsertStocks);
                    i = i + batchLimit;
                    insertLength = insertLength - batchLimit;
                    totalInsertRow += insertRow;
                    LOGGER.info("批量插入静态标的物条数={}", insertRow);
                }
                if (insertLength > 0) {
                    List<StockDto> remainingInsertStocks = toInsertStocks.subList(i, i + insertLength);
                    int insertRow = getBaseMapper().insertBatch(remainingInsertStocks);
                    totalInsertRow += insertRow;
                    LOGGER.info("批量插入静态标的物条数={}", insertRow);
                }
                return totalInsertRow;
            } else {
                return 0;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional
    public IPage<ListStockResponse> page(ListStockRequest listStockRequest) {
        QueryWrapper<StockDto> queryWrapper = Wrappers.query();
        Page<StockDto> pagination = Page.of(1, 10);
        if (listStockRequest.getName() != null) {
            queryWrapper.like("name", listStockRequest.getName());
        }
        if (listStockRequest.getStockType() != null) {
            queryWrapper.eq("stock_type", listStockRequest.getStockType());
        }
        if (listStockRequest.getMarket() != null) {
            queryWrapper.eq("market", listStockRequest.getMarket());
        }
        if (listStockRequest.getDelisting() != null) {
            queryWrapper.eq("delisting", listStockRequest.getDelisting());
        }
        if (listStockRequest.getCurrent() != null) {
            pagination.setCurrent(listStockRequest.getCurrent());
        }
        if (listStockRequest.getSize() != null) {
            pagination.setSize(listStockRequest.getSize());
        }
        return page(pagination, queryWrapper).convert(stockDto -> {
            ListStockResponse response = new ListStockResponse();
            response.setId(stockDto.getId());
            response.setName(stockDto.getName());
            response.setMarket(MarketType.getNameByCode(stockDto.getMarket()));
            response.setMarketCode(stockDto.getMarket());
            response.setCode(stockDto.getCode());
            response.setLotSize(stockDto.getLotSize());
            response.setStockType(StockType.getNameByCode(stockDto.getStockType()));
            response.setStockTypeCode(stockDto.getStockType());
//                    response.setStockChildType(StockType.getNameByCode(stockDto.getStockChildType()));
//                    response.setStockOwner(stockDto.getStockOwner());
//                    response.setOptionType(stockDto.getOptionType());
//                    response.setStrikeTime(stockDto.getStrikeTime());
//                    response.setStrikePrice(stockDto.getStrikePrice());
//                    response.setSuspension(stockDto.getSuspension());
            response.setListingDate(stockDto.getListingDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//                    response.setStockId(stockDto.getStockId());
            response.setDelisting(DelistingType.getNameByCode(stockDto.getDelisting()));
//                    response.setIndexOptionType(stockDto.getIndexOptionType());
//                    response.setMainContract(stockDto.getMainContract());
//                    response.setLastTradeTime(stockDto.getLastTradeTime());
            response.setExchangeType(ExchangeType.getNameByCode(stockDto.getExchangeType()));
            return response;
        });
    }
}




