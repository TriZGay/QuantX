package io.futakotome.sec.controller;

import io.futakotome.sec.controller.vo.AddPermissionRequest;
import io.futakotome.sec.controller.vo.CommonResult;
import io.futakotome.sec.controller.vo.ListPermissionRequest;
import io.futakotome.sec.controller.vo.UpdatePermissionRequest;
import io.futakotome.sec.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

        });
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<CommonResult>> addPermission(@RequestBody @Validated Mono<AddPermissionRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {

        });
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<CommonResult>> updateById(@PathVariable("id") Long id,
                                                         @RequestBody @Validated Mono<UpdatePermissionRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {

        });
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<CommonResult>> deleteById(@PathVariable("id") Long id) {
        return Mono.create(responseEntityMonoSink -> {

        });
    }
}
