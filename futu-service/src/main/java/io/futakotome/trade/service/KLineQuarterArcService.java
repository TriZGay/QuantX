package io.futakotome.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.futakotome.trade.dto.KLineQuarterArcDto;

import java.util.List;

/**
 * @author 86131
 * @description 针对表【t_kl_quarter_arc】的数据库操作Service
 * @createDate 2025-10-31 15:07:34
 */
public interface KLineQuarterArcService extends IService<KLineQuarterArcDto> {
    int saveHistoryKLinesQuarter(List<KLineQuarterArcDto> kLineQuarterArcDtos);
}
