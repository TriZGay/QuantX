package io.futakotome.sec.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.futakotome.sec.controller.vo.AddRoleRequest;
import io.futakotome.sec.controller.vo.CommonResult;
import io.futakotome.sec.controller.vo.ListRoleRequest;
import io.futakotome.sec.controller.vo.UpdateRoleRequest;
import io.futakotome.sec.domain.Role;
import io.futakotome.sec.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/list")
    public Mono<ResponseEntity<CommonResult>> listRole(ListRoleRequest request) {
        return Mono.create(responseEntityMonoSink -> {
            IPage<Role> pageRole = roleService.queryRoles(request);
            if (pageRole != null) {
                Utils.successWithData(responseEntityMonoSink, CommonResult.COMMON_SUCCESS,
                        "查询成功", pageRole.convert(Role::role2VoMapper));
            } else {
                Utils.success(responseEntityMonoSink, CommonResult.SERVER_EXCEPTION, "查询失败");
            }
        });
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<CommonResult>> addRole(@RequestBody @Validated Mono<AddRoleRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, Utils.badRequestHandle(responseEntityMonoSink))
                    .doOnNext(request -> {
                        if (roleService.addRole(request)) {
                            Utils.success(responseEntityMonoSink, CommonResult.COMMON_SUCCESS, "添加角色成功");
                        } else {
                            Utils.success(responseEntityMonoSink, CommonResult.COMMON_FAILED, "添加角色失败");
                        }
                    }).subscribe();
        });
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<CommonResult>> updateBy(@PathVariable("id") Long id,
                                                       @RequestBody @Validated Mono<UpdateRoleRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, Utils.badRequestHandle(responseEntityMonoSink))
                    .doOnNext(request -> {
                        if (roleService.updateRoleById(id, request)) {
                            Utils.success(responseEntityMonoSink, CommonResult.COMMON_SUCCESS, "修改角色成功");
                        } else {
                            Utils.success(responseEntityMonoSink, CommonResult.COMMON_FAILED, "修改角色失败");
                        }
                    }).subscribe();
        });
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<CommonResult>> deleteBy(@PathVariable("id") Long id) {
        return Mono.create(responseEntityMonoSink -> {
            if (roleService.deleteRoleById(id)) {
                Utils.success(responseEntityMonoSink, CommonResult.COMMON_SUCCESS, "删除角色成功");
            } else {
                Utils.success(responseEntityMonoSink, CommonResult.COMMON_FAILED, "删除角色失败");
            }
        });
    }

}
