package io.futakotome.sec.controller;

import io.futakotome.sec.controller.vo.CommonResult;
import io.futakotome.sec.controller.vo.ListRoleRequest;
import io.futakotome.sec.service.RoleDtoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    }
}
