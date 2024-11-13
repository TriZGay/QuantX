package io.futakotome.sec.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.sec.controller.vo.AddUserRequest;
import io.futakotome.sec.controller.vo.ListUserRequest;
import io.futakotome.sec.controller.vo.UpdateUserRequest;
import io.futakotome.sec.domain.User;
import io.futakotome.sec.dto.UserDto;
import io.futakotome.sec.mapper.UserDtoMapper;
import io.futakotome.sec.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pc
 * @description 针对表【t_user】的数据库操作Service实现
 * @createDate 2023-11-08 14:34:25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDtoMapper, UserDto>
        implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDtoMapper userDtoMapper;

    public UserServiceImpl(UserDtoMapper userDtoMapper) {
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public IPage<User> queryUsers(ListUserRequest request) {
        try {
            Page<UserDto> pagination = Page.of(1, 10);
            QueryWrapper<UserDto> queryWrapper = Wrappers.query();
            if (request.getCurrent() != null) {
                pagination.setCurrent(request.getCurrent());
            }
            if (request.getSize() != null) {
                pagination.setSize(request.getSize());
            }
            if (request.getName() != null) {
                queryWrapper.like("username", request.getName());
            }
            return page(pagination, queryWrapper).convert(User::dto2UserMapper);
        } catch (Exception e) {
            LOGGER.error("查询用户失败", e);
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(AddUserRequest request) {
        UserDto dto = User.transferAddReq(request);
        try {
            return userDtoMapper.insertAll(dto) > 0;
        } catch (Exception e) {
            LOGGER.error("新增用户失败", e);
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(Long id, UpdateUserRequest request) {
        UserDto dto = User.transferUpdReq(id, request);
        try {
            return userDtoMapper.updateSelective(dto) > 0;
        } catch (Exception e) {
            LOGGER.error("修改用户失败", e);
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        try {
            return removeById(id);
        } catch (Exception e) {
            LOGGER.error("删除用户失败", e);
            return false;
        }
    }
}




