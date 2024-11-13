package io.futakotome.sec.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.futakotome.sec.controller.vo.AddPermissionRequest;
import io.futakotome.sec.controller.vo.ListPermissionRequest;
import io.futakotome.sec.controller.vo.UpdatePermissionRequest;
import io.futakotome.sec.domain.Permission;
import io.futakotome.sec.dto.PermissionDto;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author pc
 * @description 针对表【t_permission】的数据库操作Service
 * @createDate 2023-11-08 14:36:36
 */
public interface PermissionService extends IService<PermissionDto> {
    IPage<Permission> queryPermission(ListPermissionRequest listPermissionRequest);

    boolean addPermission(AddPermissionRequest addPermissionRequest);

    boolean updatePermissionById(Long id, UpdatePermissionRequest updatePermissionRequest);

    boolean deletePermissionById(Long id);
}
