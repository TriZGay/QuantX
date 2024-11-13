package io.futakotome.sec.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.futakotome.sec.controller.vo.AddUserRequest;
import io.futakotome.sec.controller.vo.CommonResult;
import io.futakotome.sec.controller.vo.ListUserRequest;
import io.futakotome.sec.controller.vo.UpdateUserRequest;
import io.futakotome.sec.domain.User;
import io.futakotome.sec.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/list")
    public Mono<ResponseEntity<CommonResult>> listUsers(ListUserRequest request) {
        return Mono.create(responseEntityMonoSink -> {
            IPage<User> userIPage = userService.queryUsers(request);
            if (userIPage != null) {
                Utils.successWithData(responseEntityMonoSink, CommonResult.COMMON_SUCCESS, "查询成功",
                        userIPage.convert(User::user2VoMapper));
            } else {
                Utils.success(responseEntityMonoSink, CommonResult.SERVER_EXCEPTION, "查询失败");
            }
        });
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<CommonResult>> addUser(@RequestBody @Validated Mono<AddUserRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, Utils.badRequestHandle(responseEntityMonoSink))
                    .doOnNext(request -> {
                        if (userService.addUser(request)) {
                            Utils.success(responseEntityMonoSink, CommonResult.COMMON_SUCCESS, "新增成功");
                        } else {
                            Utils.success(responseEntityMonoSink, CommonResult.COMMON_FAILED, "新增失败");
                        }
                    }).subscribe();
        });
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<CommonResult>> updateById(@PathVariable("id") Long id, @RequestBody @Validated Mono<UpdateUserRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, Utils.badRequestHandle(responseEntityMonoSink))
                    .doOnNext(request -> {
                        if (userService.updateById(id, request)) {
                            Utils.success(responseEntityMonoSink, CommonResult.COMMON_SUCCESS, "修改成功");
                        } else {
                            Utils.success(responseEntityMonoSink, CommonResult.COMMON_FAILED, "修改失败");
                        }
                    }).subscribe();
        });
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<CommonResult>> deleteById(@PathVariable("id") Long id) {
        return Mono.create(responseEntityMonoSink -> {
            if (userService.deleteById(id)) {
                Utils.success(responseEntityMonoSink, CommonResult.COMMON_SUCCESS, "删除成功");
            } else {
                Utils.success(responseEntityMonoSink, CommonResult.COMMON_FAILED, "删除失败");
            }
        });
    }
}
