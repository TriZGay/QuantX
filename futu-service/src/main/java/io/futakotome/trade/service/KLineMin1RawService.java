package io.futakotome.trade.service;

import io.futakotome.trade.dto.KLineMin1RawDto;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86131
* @description 针对表【t_kl_min_1_raw】的数据库操作Service
* @createDate 2025-10-15 23:23:34
*/
public interface KLineMin1RawService extends IService<KLineMin1RawDto> {
    void saveOne(KLineMin1RawDto kLineMin1RawDto);
}
