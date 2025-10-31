package io.futakotome.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.futakotome.trade.dto.KLineMonthArcDto;

import java.util.List;

/**
 * @author 86131
 * @description 针对表【t_kl_month_arc】的数据库操作Service
 * @createDate 2025-10-30 14:57:36
 */
public interface KLineMonthArcService extends IService<KLineMonthArcDto> {

    int saveHistoryKLinesMonth(List<KLineMonthArcDto> kLineMonthArcDtos);
}
