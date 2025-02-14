package io.futakotome.trade.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.futakotome.trade.controller.vo.ListPlateRequest;
import io.futakotome.trade.controller.vo.ListPlateResponse;
import io.futakotome.trade.dto.PlateDto;

import java.util.List;

public interface PlateDtoService extends IService<PlateDto> {
    int insertBatch(List<PlateDto> list);

    IPage<ListPlateResponse> page(ListPlateRequest request);
}
