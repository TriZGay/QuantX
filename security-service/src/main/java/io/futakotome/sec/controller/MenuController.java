package io.futakotome.sec.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.futakotome.sec.controller.vo.AddMenuRequest;
import io.futakotome.sec.controller.vo.CommonResult;
import io.futakotome.sec.controller.vo.ListMenuRequest;
import io.futakotome.sec.controller.vo.UpdateMenuRequest;
import io.futakotome.sec.domain.Menu;
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
            IPage<Menu> page = menuService.queryMenus(request);
            if (page != null) {
                Utils.successWithData(responseEntityMonoSink, CommonResult.COMMON_SUCCESS,
                        "查询成功", page.convert(Menu::menu2VoMapper));
            } else {
                Utils.success(responseEntityMonoSink, CommonResult.SERVER_EXCEPTION, "查询失败");
            }
        });
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<CommonResult>> addMenu(@RequestBody @Validated Mono<AddMenuRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, Utils.badRequestHandle(responseEntityMonoSink))
                    .doOnNext(request -> {
                        if (menuService.addMenu(request)) {
                            Utils.success(responseEntityMonoSink, CommonResult.COMMON_SUCCESS, "添加菜单成功");
                        } else {
                            Utils.success(responseEntityMonoSink, CommonResult.COMMON_FAILED, "添加菜单失败");
                        }
                    }).subscribe();
        });
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<CommonResult>> updateMenu(@PathVariable("id") Long id,
                                                         @RequestBody @Validated Mono<UpdateMenuRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, Utils.badRequestHandle(responseEntityMonoSink))
                    .doOnNext(updateMenuRequest -> {
                        if (menuService.updateMenuById(id, updateMenuRequest)) {
                            Utils.success(responseEntityMonoSink, CommonResult.COMMON_SUCCESS, "修改菜单成功");
                        } else {
                            Utils.success(responseEntityMonoSink, CommonResult.COMMON_FAILED, "修改菜单失败");
                        }
                    }).subscribe();
        });
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<CommonResult>> deleteMenu(@PathVariable("id") Long id) {
        return Mono.create(responseEntityMonoSink -> {
            if (menuService.deleteBy(id)) {
                Utils.success(responseEntityMonoSink, CommonResult.COMMON_SUCCESS, "删除菜单成功");
            } else {
                Utils.success(responseEntityMonoSink, CommonResult.COMMON_FAILED, "删除菜单失败");
            }
        });
    }


}
