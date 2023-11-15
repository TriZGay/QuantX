package io.futakotome.sec.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.sec.controller.vo.AddPermissionRequest;
import io.futakotome.sec.controller.vo.ListPermissionRequest;
import io.futakotome.sec.controller.vo.UpdatePermissionRequest;
import io.futakotome.sec.domain.Permission;
import io.futakotome.sec.dto.PermissionDto;
import io.futakotome.sec.service.PermissionService;
import io.futakotome.sec.mapper.PermissionDtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pc
 * @description 针对表【t_permission】的数据库操作Service实现
 * @createDate 2023-11-08 14:36:36
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionDtoMapper, PermissionDto>
        implements PermissionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionServiceImpl.class);
    private final PermissionDtoMapper permissionDtoMapper;

    public PermissionServiceImpl(PermissionDtoMapper permissionDtoMapper) {
        this.permissionDtoMapper = permissionDtoMapper;
    }

    @Override
    public IPage<Permission> queryPermission(ListPermissionRequest listPermissionRequest) {
        try {
            Page<PermissionDto> pagination = Page.of(1, 10);
            QueryWrapper<PermissionDto> queryWrapper = Wrappers.query();
            if (listPermissionRequest.getCurrent() != null) {
                pagination.setCurrent(listPermissionRequest.getCurrent());
            }
            if (listPermissionRequest.getSize() != null) {
                pagination.setSize(listPermissionRequest.getSize());
            }
            if (listPermissionRequest.getName() != null) {
                queryWrapper.like("name", listPermissionRequest.getName());
            }
            return page(pagination, queryWrapper).convert(Permission::dto2Permission);
        } catch (Exception e) {
            LOGGER.error("查询权限失败", e);
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addPermission(AddPermissionRequest addPermissionRequest) {
        PermissionDto permissionDto = Permission.transferAddReq(addPermissionRequest);
        try {
            return permissionDtoMapper.insertAll(permissionDto) > 0;
        } catch (Exception e) {
            LOGGER.error("新增权限失败", e);
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePermissionById(Long id, UpdatePermissionRequest updatePermissionRequest) {
        PermissionDto permissionDto = Permission.transferUpdReq(id, updatePermissionRequest);
        try {
            return permissionDtoMapper.updateSelective(permissionDto) > 0;
        } catch (Exception e) {
            LOGGER.error("修改权限失败", e);
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deletePermissionById(Long id) {
        try {
            return removeById(id);
        } catch (Exception e) {
            LOGGER.error("删除权限失败", e);
            return false;
        }
    }
}




