package io.futakotome.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.futakotome.trade.dto.KLineDayArcDto;

import java.util.List;

/**
* @author 86131
* @description 针对表【t_kl_day_arc】的数据库操作Service
* @createDate 2025-10-30 10:56:58
*/
public interface KLineDayArcService extends IService<KLineDayArcDto> {
    int saveHistoryKLinesDay(List<KLineDayArcDto> kLineMin1ArcDto);
}
