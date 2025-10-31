package io.futakotome.trade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.trade.dto.KLineWeekArcDto;
import io.futakotome.trade.mapper.ck.KLineWeekArcDtoMapper;
import io.futakotome.trade.service.KLineWeekArcDtoService;
import io.futakotome.trade.utils.ListSplitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 86131
 * @description 针对表【t_kl_week_arc】的数据库操作Service实现
 * @createDate 2025-10-31 15:39:43
 */
@Service
public class KLineWeekArcDtoServiceImpl extends ServiceImpl<KLineWeekArcDtoMapper, KLineWeekArcDto>
        implements KLineWeekArcDtoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KLineWeekArcDtoServiceImpl.class);

    @Override
    public int saveHistoryKLinesWeek(List<KLineWeekArcDto> kLineWeekArcDtos) {
        LOGGER.info("周级粒度历史K线待入库{}条", kLineWeekArcDtos.size());
        List<List<KLineWeekArcDto>> batchList = ListSplitUtil.splitList(kLineWeekArcDtos);
        int sum = 0;
        for (List<KLineWeekArcDto> batch : batchList) {
            sum += getBaseMapper().insertBatch(batch);
        }
        LOGGER.info("周级粒度历史K线入库{}条成功", sum);
        return sum;
    }
}




