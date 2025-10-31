package io.futakotome.trade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.trade.dto.KLineYearArcDto;
import io.futakotome.trade.mapper.ck.KLineYearArcDtoMapper;
import io.futakotome.trade.service.KLineYearArcService;
import io.futakotome.trade.utils.ListSplitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 86131
 * @description 针对表【t_kl_year_arc】的数据库操作Service实现
 * @createDate 2025-10-31 17:04:05
 */
@Service
public class KLineYearArcDtoServiceImpl extends ServiceImpl<KLineYearArcDtoMapper, KLineYearArcDto>
        implements KLineYearArcService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KLineYearArcDtoServiceImpl.class);

    @Override
    public int saveHistoryKLinesYear(List<KLineYearArcDto> kLineYearArcDtos) {
        LOGGER.info("年级粒度历史K线待入库{}条", kLineYearArcDtos.size());
        List<List<KLineYearArcDto>> batchList = ListSplitUtil.splitList(kLineYearArcDtos);
        int sum = 0;
        for (List<KLineYearArcDto> batch : batchList) {
            sum += getBaseMapper().insertBatch(batch);
        }
        LOGGER.info("年级粒度历史K线入库{}条成功", sum);
        return sum;
    }
}




