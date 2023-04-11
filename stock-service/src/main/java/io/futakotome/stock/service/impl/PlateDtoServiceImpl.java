package io.futakotome.stock.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.stock.dto.PlateDto;
import io.futakotome.stock.service.PlateDtoService;
import io.futakotome.stock.mapper.PlateDtoMapper;
import org.springframework.stereotype.Service;

/**
* @author pc
* @description 针对表【t_plate】的数据库操作Service实现
* @createDate 2023-04-11 10:12:42
*/
@Service
public class PlateDtoServiceImpl extends ServiceImpl<PlateDtoMapper, PlateDto>
    implements PlateDtoService{

}




