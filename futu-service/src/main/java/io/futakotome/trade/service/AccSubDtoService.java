package io.futakotome.trade.service;

import io.futakotome.trade.dto.AccSubDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86131
* @description 针对表【t_acc_sub】的数据库操作Service
* @createDate 2025-02-26 10:02:50
*/
public interface AccSubDtoService extends IService<AccSubDto> {

    int insertBatch(List<AccSubDto> accSubDtos);
}
