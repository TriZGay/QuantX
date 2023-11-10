package io.futakotome.sec.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.futakotome.sec.controller.vo.AddRoleRequest;
import io.futakotome.sec.controller.vo.ListRoleRequest;
import io.futakotome.sec.controller.vo.UpdateRoleRequest;
import io.futakotome.sec.domain.Role;
import io.futakotome.sec.dto.RoleDto;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author pc
 * @description 针对表【t_role】的数据库操作Service
 * @createDate 2023-11-08 14:30:41
 */
public interface RoleService extends IService<RoleDto> {
    IPage<Role> queryRoles(ListRoleRequest request);

    boolean addRole(AddRoleRequest request);

    boolean updateRoleById(Long id, UpdateRoleRequest request);

    boolean deleteRoleById(Long id);
}
