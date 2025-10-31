package io.futakotome.trade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.trade.dto.KLineMonthArcDto;
import io.futakotome.trade.mapper.ck.KLineMonthArcDtoMapper;
import io.futakotome.trade.service.KLineMonthArcService;
import io.futakotome.trade.utils.ListSplitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 86131
 * @description 针对表【t_kl_month_arc】的数据库操作Service实现
 * @createDate 2025-10-30 14:57:36
 */
@Service
public class KLineMonthArcServiceImpl extends ServiceImpl<KLineMonthArcDtoMapper, KLineMonthArcDto>
        implements KLineMonthArcService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KLineMonthArcServiceImpl.class);

    @Override
    public int saveHistoryKLinesMonth(List<KLineMonthArcDto> kLineMonthArcDtos) {
        LOGGER.info("月级粒度历史K线待入库{}条", kLineMonthArcDtos.size());
        List<List<KLineMonthArcDto>> batchList = ListSplitUtil.splitList(kLineMonthArcDtos);
        int sum = 0;
        for (List<KLineMonthArcDto> batch : batchList) {
            sum += getBaseMapper().insertBatch(batch);
        }
        LOGGER.info("月级粒度历史K线入库{}条成功", sum);
        return sum;
    }
}




