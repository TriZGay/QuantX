package io.futakotome.sec.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.sec.dto.PermissionDto;
import io.futakotome.sec.service.PermissionService;
import io.futakotome.sec.mapper.PermissionDtoMapper;
import org.springframework.stereotype.Service;

/**
* @author pc
* @description 针对表【t_permission】的数据库操作Service实现
* @createDate 2023-11-08 14:36:36
*/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionDtoMapper, PermissionDto>
    implements PermissionService {

}




