package io.futakotome.trade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.trade.dto.KLineMin1RawDto;
import io.futakotome.trade.service.KLineMin1RawService;
import io.futakotome.trade.mapper.ck.KLineMin1RawDtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author 86131
 * @description 针对表【t_kl_min_1_raw】的数据库操作Service实现
 * @createDate 2025-10-15 23:23:34
 */
@Service
public class KLineMin1RawServiceImpl extends ServiceImpl<KLineMin1RawDtoMapper, KLineMin1RawDto>
        implements KLineMin1RawService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KLineMin1RawServiceImpl.class);

    @Override
    public void saveOne(KLineMin1RawDto kLineMin1RawDto) {
        if (save(kLineMin1RawDto)) {
            LOGGER.info("1分钟实时K线入库成功.code={},rehab_type={},close_price={}",
                    kLineMin1RawDto.getCode(), kLineMin1RawDto.getRehabType(), kLineMin1RawDto.getClosePrice());
        }
    }
}




