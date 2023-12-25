package io.futakotome.sec.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import io.futakotome.sec.controller.vo.AddUserRequest;
import io.futakotome.sec.controller.vo.ListUserRequest;
import io.futakotome.sec.controller.vo.UpdateUserRequest;
import io.futakotome.sec.domain.User;
import io.futakotome.sec.dto.UserDto;

/**
 * @author pc
 * @description 针对表【t_user】的数据库操作Service
 * @createDate 2023-11-08 14:34:25
 */
public interface UserService extends IService<UserDto> {
    IPage<User> queryUsers(ListUserRequest request);

    boolean addUser(AddUserRequest request);

    boolean updateById(Long id, UpdateUserRequest request);

    boolean deleteById(Long id);
}
