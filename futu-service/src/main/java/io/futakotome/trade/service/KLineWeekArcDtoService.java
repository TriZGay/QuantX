package io.futakotome.trade.service;

import io.futakotome.trade.dto.KLineQuarterArcDto;
import io.futakotome.trade.dto.KLineWeekArcDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 86131
 * @description 针对表【t_kl_week_arc】的数据库操作Service
 * @createDate 2025-10-31 15:39:43
 */
public interface KLineWeekArcDtoService extends IService<KLineWeekArcDto> {
    int saveHistoryKLinesWeek(List<KLineWeekArcDto> kLineWeekArcDtos);
}
