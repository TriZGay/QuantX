package io.futakotome.sec.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.sec.dto.UserDto;
import io.futakotome.sec.service.UserService;
import io.futakotome.sec.mapper.UserDtoMapper;
import org.springframework.stereotype.Service;

/**
* @author pc
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2023-11-08 14:34:25
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserDtoMapper, UserDto>
    implements UserService {

}




