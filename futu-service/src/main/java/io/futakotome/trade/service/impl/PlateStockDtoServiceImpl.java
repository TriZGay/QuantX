package io.futakotome.trade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.trade.dto.PlateStockDto;
import io.futakotome.trade.mapper.pg.PlateStockDtoMapper;
import io.futakotome.trade.service.PlateStockDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author pc
 * @description 针对表【t_plate_stock】的数据库操作Service实现
 * @createDate 2023-04-14 15:11:03
 */
@Service
public class PlateStockDtoServiceImpl extends ServiceImpl<PlateStockDtoMapper, PlateStockDto>
        implements PlateStockDtoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlateStockDtoServiceImpl.class);
    private static final ReentrantLock LOCK = new ReentrantLock();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBatch(List<PlateStockDto> relations) {
        LOCK.lock();
        try {
            if (!relations.isEmpty()) {
                int totalInsertRow = 0;
                int insertLength = relations.size();
                int batchLimit = 1000;
                int i = 0;
                while (insertLength > batchLimit) {
                    List<PlateStockDto> batchInsertRelations = relations.subList(i, i + batchLimit);
                    int insertRow = getBaseMapper().insertBatch(batchInsertRelations);
                    i = i + batchLimit;
                    insertLength -= batchLimit;
                    totalInsertRow += insertRow;
                }
                if (insertLength > 0) {
                    List<PlateStockDto> remainingInsertRelations = relations.subList(i, i + insertLength);
                    int insertRow = getBaseMapper().insertBatch(remainingInsertRelations);
                    totalInsertRow += insertRow;

                }
                LOGGER.info("板块与标的物关系入库{}条数据.", totalInsertRow);
                return totalInsertRow;
            } else {
                LOGGER.info("没有新增关系,不需要插入.");
                return 0;
            }
        } finally {
            LOCK.unlock();
        }
    }
}




