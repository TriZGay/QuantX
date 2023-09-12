package io.futakotome.trade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.trade.dto.PositionDto;
import io.futakotome.trade.mapper.PositionDtoMapper;
import io.futakotome.trade.service.PositionDtoService;
import org.springframework.stereotype.Service;

/**
* @author pc
* @description 针对表【t_position】的数据库操作Service实现
* @createDate 2023-05-18 17:29:48
*/
@Service
public class PositionDtoServiceImpl extends ServiceImpl<PositionDtoMapper, PositionDto>
    implements PositionDtoService{

}




