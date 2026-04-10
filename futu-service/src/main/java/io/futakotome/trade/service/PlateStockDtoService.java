package io.futakotome.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.futakotome.trade.dto.PlateStockDto;

import java.util.List;

public interface PlateStockDtoService extends IService<PlateStockDto> {
    int insertBatch(List<PlateStockDto> relations);
}
