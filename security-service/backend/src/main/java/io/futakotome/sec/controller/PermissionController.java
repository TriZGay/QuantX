package io.futakotome.sec.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.futakotome.sec.controller.vo.AddPermissionRequest;
import io.futakotome.sec.controller.vo.CommonResult;
import io.futakotome.sec.controller.vo.ListPermissionRequest;
import io.futakotome.sec.controller.vo.UpdatePermissionRequest;
import io.futakotome.sec.domain.Permission;
import io.futakotome.sec.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@RequestMapping("/permission")
@RestController
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/list")
    public Mono<ResponseEntity<CommonResult>> listPermissions(ListPermissionRequest listPermissionRequest) {
        return Mono.create(responseEntityMonoSink -> {
            IPage<Permission> permissionIPage = permissionService.queryPermission(listPermissionRequest);
            if (permissionIPage != null) {
                Utils.successWithData(responseEntityMonoSink, CommonResult.COMMON_SUCCESS, "查询成功",
                        permissionIPage.convert(Permission::permission2Vo));
            } else {
                Utils.success(responseEntityMonoSink, CommonResult.SERVER_EXCEPTION, "查询失败");
            }
        });
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<CommonResult>> addPermission(@RequestBody @Validated Mono<AddPermissionRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, Utils.badRequestHandle(responseEntityMonoSink))
                    .doOnNext(addPermissionRequest -> {
                        if (permissionService.addPermission(addPermissionRequest)) {
                            Utils.success(responseEntityMonoSink, CommonResult.COMMON_SUCCESS, "新增权限成功");
                        } else {
                            Utils.success(responseEntityMonoSink, CommonResult.COMMON_FAILED, "新增权限失败");
                        }
                    }).subscribe();
        });
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<CommonResult>> updateById(@PathVariable("id") Long id,
                                                         @RequestBody @Validated Mono<UpdatePermissionRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, Utils.badRequestHandle(responseEntityMonoSink))
                    .doOnNext(updatePermissionRequest -> {
                        if (permissionService.updatePermissionById(id, updatePermissionRequest)) {
                            Utils.success(responseEntityMonoSink, CommonResult.COMMON_SUCCESS, "修改权限成功");
                        } else {
                            Utils.success(responseEntityMonoSink, CommonResult.COMMON_FAILED, "修改权限失败");
                        }
                    }).subscribe();
        });
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<CommonResult>> deleteById(@PathVariable("id") Long id) {
        return Mono.create(responseEntityMonoSink -> {
            if (permissionService.deletePermissionById(id)) {
                Utils.success(responseEntityMonoSink, CommonResult.COMMON_SUCCESS, "删除权限成功");
            } else {
                Utils.success(responseEntityMonoSink, CommonResult.COMMON_FAILED, "删除权限失败");
            }
        });
    }
}
