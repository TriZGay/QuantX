package io.futakotome.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.futakotome.trade.dto.KLineMin1ArcDto;

import java.util.List;

/**
 * @author 86131
 * @description 针对表【t_kl_min_1_arc】的数据库操作Service
 * @createDate 2025-10-12 13:49:19
 */
public interface KLineMin1ArcService extends IService<KLineMin1ArcDto> {
    int saveHistoryKLinesMin1(List<KLineMin1ArcDto> kLineMin1ArcDto);
}
