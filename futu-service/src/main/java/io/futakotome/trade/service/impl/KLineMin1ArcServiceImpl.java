package io.futakotome.trade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.trade.dto.KLineMin1ArcDto;
import io.futakotome.trade.service.KLineMin1ArcService;
import io.futakotome.trade.mapper.ck.KLineMin1ArcMapper;
import io.futakotome.trade.utils.ListSplitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 86131
 * @description 针对表【t_kl_min_1_arc】的数据库操作Service实现
 * @createDate 2025-10-12 13:49:19
 */
@Service
public class KLineMin1ArcServiceImpl extends ServiceImpl<KLineMin1ArcMapper, KLineMin1ArcDto>
        implements KLineMin1ArcService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KLineMin1ArcServiceImpl.class);

    @Override
    public int saveHistoryKLinesMin1(List<KLineMin1ArcDto> kLineMin1ArcDto) {
        LOGGER.info("1分钟粒度历史K线待入库{}条", kLineMin1ArcDto.size());
        List<List<KLineMin1ArcDto>> batchList = ListSplitUtil.splitList(kLineMin1ArcDto);
        int sum = 0;
        for (List<KLineMin1ArcDto> batch : batchList) {
            sum += getBaseMapper().insertBatch(batch);
        }
        LOGGER.info("1分钟粒度历史K线入库{}条成功", sum);
        return sum;
    }
}




