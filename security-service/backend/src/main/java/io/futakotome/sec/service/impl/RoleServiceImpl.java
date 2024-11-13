package io.futakotome.sec.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.sec.controller.vo.AddRoleRequest;
import io.futakotome.sec.controller.vo.ListRoleRequest;
import io.futakotome.sec.controller.vo.UpdateRoleRequest;
import io.futakotome.sec.domain.Role;
import io.futakotome.sec.dto.RoleDto;
import io.futakotome.sec.service.RoleService;
import io.futakotome.sec.mapper.RoleDtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pc
 * @description 针对表【t_role】的数据库操作Service实现
 * @createDate 2023-11-08 14:30:41
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDtoMapper, RoleDto>
        implements RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);
    private final RoleDtoMapper roleDtoMapper;

    public RoleServiceImpl(RoleDtoMapper roleDtoMapper) {
        this.roleDtoMapper = roleDtoMapper;
    }

    @Override
    public IPage<Role> queryRoles(ListRoleRequest request) {
        try {
            Page<RoleDto> pagination = Page.of(1, 10);
            QueryWrapper<RoleDto> queryWrapper = Wrappers.query();
            if (request.getCurrent() != null) {
                pagination.setCurrent(request.getCurrent());
            }
            if (request.getSize() != null) {
                pagination.setSize(request.getSize());
            }
            if (request.getName() != null) {
                queryWrapper.like("name", request.getName());
            }
            return page(pagination, queryWrapper).convert(Role::dto2RoleMapper);
        } catch (Exception e) {
            LOGGER.error("查询角色失败", e);
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addRole(AddRoleRequest request) {
        RoleDto addRole = Role.transferAddReq(request);
        try {
            return roleDtoMapper.insertAll(addRole) > 0;
        } catch (Exception e) {
            LOGGER.error("新增角色失败", e);
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRoleById(Long id, UpdateRoleRequest request) {
        RoleDto updateRole = Role.transferUpdReq(id, request);
        try {
            return roleDtoMapper.updateSelective(updateRole) > 0;
        } catch (Exception e) {
            LOGGER.error("更新角色失败", e);
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRoleById(Long id) {
        try {
            return removeById(id);
        } catch (Exception e) {
            LOGGER.error("删除角色失败", e);
            return false;
        }
    }
}




