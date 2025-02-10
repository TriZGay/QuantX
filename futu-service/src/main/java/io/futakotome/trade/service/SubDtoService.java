package io.futakotome.trade.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.futakotome.trade.controller.vo.ListSubscribeRequest;
import io.futakotome.trade.controller.vo.ListSubscribeResponse;
import io.futakotome.trade.dto.SubDto;

/**
 * @author pc
 * @description 针对表【t_sub】的数据库操作Service
 * @createDate 2023-05-08 15:00:30
 */
public interface SubDtoService extends IService<SubDto> {
    IPage<ListSubscribeResponse> findDetails(ListSubscribeRequest request);

    IPage<ListSubscribeResponse> findList(ListSubscribeRequest request);
}
