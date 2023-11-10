package io.futakotome.sec.controller;

import io.futakotome.sec.controller.vo.AddMenuRequest;
import io.futakotome.sec.controller.vo.CommonResult;
import io.futakotome.sec.controller.vo.ListMenuRequest;
import io.futakotome.sec.controller.vo.UpdateMenuRequest;
import io.futakotome.sec.service.MenuService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/list")
    public Mono<ResponseEntity<CommonResult>> listMenu(ListMenuRequest request) {
        return Mono.create(responseEntityMonoSink -> {

        });
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<CommonResult>> addMenu(@RequestBody @Validated Mono<AddMenuRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, throwable -> {
                responseEntityMonoSink.success(ResponseEntity.badRequest().body(new CommonResult.Builder()
                        .code(CommonResult.SERVER_EXCEPTION)
                        .msg(throwable.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .collect(Collectors.joining()))
                        .build()));
            }).doOnNext(request -> {
                if (menuService.addMenu(request)) {
                    responseEntityMonoSink.success(ResponseEntity.ok(new CommonResult.Builder()
                            .code(CommonResult.COMMON_SUCCESS)
                            .msg("添加菜单成功")
                            .build()));
                } else {
                    responseEntityMonoSink.success(ResponseEntity.ok(new CommonResult.Builder()
                            .code(CommonResult.COMMON_FAILED)
                            .msg("添加菜单失败")
                            .build()));
                }
            }).subscribe();
        });
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<CommonResult>> updateMenu(@PathVariable("id") Long id,
                                                         @RequestBody @Validated UpdateMenuRequest updateMenuRequest) {
        return Mono.create(responseEntityMonoSink -> {

        });
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<CommonResult>> deleteMenu(@PathVariable("id") Long id) {
        return Mono.create(responseEntityMonoSink -> {
            if (menuService.deleteBy(id)) {
                responseEntityMonoSink.success(ResponseEntity.ok(new CommonResult.Builder()
                        .code(CommonResult.COMMON_SUCCESS)
                        .msg("删除菜单成功")
                        .build()));
            } else {
                responseEntityMonoSink.success(ResponseEntity.ok(new CommonResult.Builder()
                        .code(CommonResult.COMMON_FAILED)
                        .msg("删除菜单失败")
                        .build()));
            }
        });
    }


}
