package io.futakotome.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.futakotome.trade.dto.KLineYearArcDto;

import java.util.List;

/**
 * @author 86131
 * @description 针对表【t_kl_year_arc】的数据库操作Service
 * @createDate 2025-10-31 17:04:05
 */
public interface KLineYearArcService extends IService<KLineYearArcDto> {
    int saveHistoryKLinesYear(List<KLineYearArcDto> kLineYearArcDtos);
}
