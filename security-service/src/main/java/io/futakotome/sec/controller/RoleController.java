package io.futakotome.sec.controller;

import io.futakotome.sec.controller.vo.AddRoleRequest;
import io.futakotome.sec.controller.vo.CommonResult;
import io.futakotome.sec.controller.vo.ListRoleRequest;
import io.futakotome.sec.controller.vo.UpdateRoleRequest;
import io.futakotome.sec.service.RoleDtoService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

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

        });
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<CommonResult>> addRole(@RequestBody @Validated Mono<AddRoleRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, throwable -> {
                responseEntityMonoSink.success(ResponseEntity.badRequest().body(new CommonResult.Builder()
                        .code(CommonResult.SERVER_EXCEPTION)
                        .msg(throwable.getFieldErrors().toString())
                        .build()));
            }).doOnNext(request -> {
                //todo 需要测试异常情况
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
                        .msg(throwable.getFieldErrors().toString())
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
        });
    }


}
