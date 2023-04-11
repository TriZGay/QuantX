package io.futakotome.stock.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.stock.dto.StockDto;
import io.futakotome.stock.service.StockDtoService;
import io.futakotome.stock.mapper.StockDtoMapper;
import org.springframework.stereotype.Service;

/**
* @author pc
* @description 针对表【t_stock】的数据库操作Service实现
* @createDate 2023-04-11 16:26:26
*/
@Service
public class StockDtoServiceImpl extends ServiceImpl<StockDtoMapper, StockDto>
    implements StockDtoService{

}




