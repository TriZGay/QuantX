package io.futakotome.trade.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.futakotome.trade.controller.vo.ListPlateRequest;
import io.futakotome.trade.controller.vo.ListPlateResponse;
import io.futakotome.trade.dto.PlateDto;
import io.futakotome.trade.dto.StockDto;

import java.util.List;

public interface PlateDtoService extends IService<PlateDto> {
    int insertBatch(List<PlateDto> list);

    int insertBatch(StockDto stockDto, List<PlateDto> toInsertPlates);

    IPage<ListPlateResponse> page(ListPlateRequest request);
}
