package io.futakotome.sec.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.sec.dto.RoleDto;
import io.futakotome.sec.service.RoleDtoService;
import io.futakotome.sec.mapper.RoleDtoMapper;
import org.springframework.stereotype.Service;

/**
* @author pc
* @description 针对表【t_role】的数据库操作Service实现
* @createDate 2023-11-08 14:30:41
*/
@Service
public class RoleDtoServiceImpl extends ServiceImpl<RoleDtoMapper, RoleDto>
    implements RoleDtoService{

}




