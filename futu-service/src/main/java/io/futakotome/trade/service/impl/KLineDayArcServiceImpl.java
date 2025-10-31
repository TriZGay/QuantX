package io.futakotome.trade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.trade.dto.KLineDayArcDto;
import io.futakotome.trade.mapper.ck.KLineDayArcDtoMapper;
import io.futakotome.trade.service.KLineDayArcService;
import io.futakotome.trade.utils.ListSplitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 86131
 * @description 针对表【t_kl_day_arc】的数据库操作Service实现
 * @createDate 2025-10-30 10:56:58
 */
@Service
public class KLineDayArcServiceImpl extends ServiceImpl<KLineDayArcDtoMapper, KLineDayArcDto>
        implements KLineDayArcService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KLineDayArcServiceImpl.class);

    @Override
    public int saveHistoryKLinesDay(List<KLineDayArcDto> kLineDayArcDto) {
        LOGGER.info("天级粒度历史K线待入库{}条", kLineDayArcDto.size());
        List<List<KLineDayArcDto>> batchList = ListSplitUtil.splitList(kLineDayArcDto);
        int sum = 0;
        for (List<KLineDayArcDto> batch : batchList) {
            sum += getBaseMapper().insertBatch(batch);
        }
        LOGGER.info("天级粒度历史K线入库{}条成功", sum);
        return sum;
    }
}




