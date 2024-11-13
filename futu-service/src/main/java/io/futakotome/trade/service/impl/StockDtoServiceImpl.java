package io.futakotome.trade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.trade.dto.StockDto;
import io.futakotome.trade.mapper.StockDtoMapper;
import io.futakotome.trade.service.StockDtoService;
import org.springframework.stereotype.Service;

/**
 * @author pc
 * @description 针对表【t_stock】的数据库操作Service实现
 * @createDate 2023-04-11 16:26:26
 */
@Service
public class StockDtoServiceImpl extends ServiceImpl<StockDtoMapper, StockDto>
        implements StockDtoService {
    
}




