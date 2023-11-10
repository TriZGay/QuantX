package io.futakotome.sec.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.futakotome.sec.controller.vo.AddRoleRequest;
import io.futakotome.sec.controller.vo.CommonResult;
import io.futakotome.sec.controller.vo.ListRoleRequest;
import io.futakotome.sec.controller.vo.UpdateRoleRequest;
import io.futakotome.sec.domain.Role;
import io.futakotome.sec.service.RoleDtoService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
public class RoleController {
    private final RoleDtoService roleService;

    public RoleController(RoleDtoService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/list")
    public Mono<ResponseEntity<CommonResult>> listRole(ListRoleRequest request) {
        return Mono.create(responseEntityMonoSink -> {
            IPage<Role> pageRole = roleService.queryRoles(request);
            if (pageRole != null) {
                responseEntityMonoSink.success(ResponseEntity.ok(new CommonResult.Builder()
                        .code(CommonResult.COMMON_SUCCESS)
                        .msg("查询成功")
                        .data(pageRole.convert(Role::role2VoMapper))
                        .build()));
            } else {
                responseEntityMonoSink.success(ResponseEntity.ok(new CommonResult.Builder()
                        .code(CommonResult.SERVER_EXCEPTION)
                        .msg("查询失败")
                        .build()));
            }
        });
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<CommonResult>> addRole(@RequestBody @Validated Mono<AddRoleRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, throwable -> {
                responseEntityMonoSink.success(ResponseEntity.badRequest().body(new CommonResult.Builder()
                        .code(CommonResult.SERVER_EXCEPTION)
                        .msg(throwable.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .collect(Collectors.joining()))
                        .build()));
            }).doOnNext(request -> {
                if (roleService.addRole(request)) {
                    responseEntityMonoSink.success(ResponseEntity.ok(new CommonResult.Builder()
                            .code(CommonResult.COMMON_SUCCESS)
                            .msg("添加角色成功")
                            .build()));
                } else {
                    responseEntityMonoSink.success(ResponseEntity.ok(new CommonResult.Builder()
                            .code(CommonResult.COMMON_FAILED)
                            .msg("添加角色失败")
                            .build()));
                }
            }).subscribe();
        });
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<CommonResult>> updateBy(@PathVariable("id") Long id,
                                                       @RequestBody @Validated Mono<UpdateRoleRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, throwable -> {
                responseEntityMonoSink.success(ResponseEntity.badRequest().body(new CommonResult.Builder()
                        .code(CommonResult.SERVER_EXCEPTION)
                        .msg(throwable.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .collect(Collectors.joining()))
                        .build()));
            }).doOnNext(request -> {
                if (roleService.updateRoleById(id, request)) {
                    responseEntityMonoSink.success(ResponseEntity.ok(new CommonResult.Builder()
                            .code(CommonResult.COMMON_SUCCESS)
                            .msg("修改角色成功")
                            .build()));
                } else {
                    responseEntityMonoSink.success(ResponseEntity.ok(new CommonResult.Builder()
                            .code(CommonResult.COMMON_FAILED)
                            .msg("修改角色失败")
                            .build()));
                }
            }).subscribe();
        });
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<CommonResult>> deleteBy(@PathVariable("id") Long id) {
        return Mono.create(responseEntityMonoSink -> {
            if (roleService.deleteRoleById(id)) {
                responseEntityMonoSink.success(ResponseEntity.ok(new CommonResult.Builder()
                        .code(CommonResult.COMMON_SUCCESS)
                        .msg("删除角色成功")
                        .build()));
            } else {
                responseEntityMonoSink.success(ResponseEntity.ok(new CommonResult.Builder()
                        .code(CommonResult.COMMON_FAILED)
                        .msg("删除角色失败")
                        .build()));
            }
        });
    }

}
