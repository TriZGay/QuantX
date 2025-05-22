package io.futakotome.trade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.trade.dto.AccSubDto;
import io.futakotome.trade.mapper.AccSubDtoMapper;
import io.futakotome.trade.service.AccSubDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 86131
 * @description 针对表【t_acc_sub】的数据库操作Service实现
 * @createDate 2025-02-26 10:02:50
 */
@Service
public class AccSubDtoServiceImpl extends ServiceImpl<AccSubDtoMapper, AccSubDto>
        implements AccSubDtoService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBatch(List<AccSubDto> accSubDtos) {
        List<AccSubDto> existAccSubDtos = list();
        accSubDtos.removeIf(existAccSubDtos::contains);
        if (!accSubDtos.isEmpty()) {
            return getBaseMapper().insertBatch(accSubDtos);
        }
        return 0;
    }
}




