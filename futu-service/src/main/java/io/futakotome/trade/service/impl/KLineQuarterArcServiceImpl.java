package io.futakotome.trade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.trade.dto.KLineQuarterArcDto;
import io.futakotome.trade.mapper.ck.KLineQuarterArcDtoMapper;
import io.futakotome.trade.service.KLineQuarterArcService;
import io.futakotome.trade.utils.ListSplitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 86131
 * @description 针对表【t_kl_quarter_arc】的数据库操作Service实现
 * @createDate 2025-10-31 15:07:34
 */
@Service
public class KLineQuarterArcServiceImpl extends ServiceImpl<KLineQuarterArcDtoMapper, KLineQuarterArcDto>
        implements KLineQuarterArcService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KLineQuarterArcServiceImpl.class);

    @Override
    public int saveHistoryKLinesQuarter(List<KLineQuarterArcDto> kLineQuarterArcDtos) {
        LOGGER.info("季级粒度历史K线待入库{}条", kLineQuarterArcDtos.size());
        List<List<KLineQuarterArcDto>> batchList = ListSplitUtil.splitList(kLineQuarterArcDtos);
        int sum = 0;
        for (List<KLineQuarterArcDto> batch : batchList) {
            sum += getBaseMapper().insertBatch(batch);
        }
        LOGGER.info("季级粒度历史K线入库{}条成功", sum);
        return sum;
    }
}




