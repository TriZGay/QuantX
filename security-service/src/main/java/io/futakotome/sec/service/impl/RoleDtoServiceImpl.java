package io.futakotome.sec.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.futakotome.sec.controller.vo.AddRoleRequest;
import io.futakotome.sec.controller.vo.ListRoleRequest;
import io.futakotome.sec.controller.vo.UpdateRoleRequest;
import io.futakotome.sec.domain.Role;
import io.futakotome.sec.dto.RoleDto;
import io.futakotome.sec.service.RoleDtoService;
import io.futakotome.sec.mapper.RoleDtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pc
 * @description 针对表【t_role】的数据库操作Service实现
 * @createDate 2023-11-08 14:30:41
 */
@Service
public class RoleDtoServiceImpl extends ServiceImpl<RoleDtoMapper, RoleDto>
        implements RoleDtoService {

    @Override
    public Page<Role> queryRoles(ListRoleRequest request) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addRole(AddRoleRequest request) {
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRoleById(Long id, UpdateRoleRequest request) {
        return false;
    }
}




