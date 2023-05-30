package io.futakotome.trade.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.futakotome.trade.controller.vo.ListSubscribeRequest;
import io.futakotome.trade.controller.vo.SubscribeRequest;
import io.futakotome.trade.dto.SubDto;
import io.futakotome.trade.mapper.SubDtoMapper;
import io.futakotome.trade.service.FTQotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sub")
public class SubscribeController {
    private final FTQotService ftQotService;
    private final SubDtoMapper subDtoMapper;

    public SubscribeController(FTQotService ftQotService, SubDtoMapper subDtoMapper) {
        this.ftQotService = ftQotService;
        this.subDtoMapper = subDtoMapper;
    }

    @PostMapping("/list")
    public Mono<IPage<SubDto>> listSubscribeList(@RequestBody ListSubscribeRequest request,
                                                 @RequestParam(required = false) Long page,
                                                 @RequestParam(required = false) Long limit) {
        QueryWrapper<SubDto> queryWrapper = Wrappers.query();
        Page<SubDto> pagination = Page.of(1, 10);
        if (request.getMarket() != null) {
            queryWrapper.eq("security_market", request.getMarket());
        }
        if (request.getCode() != null) {
            queryWrapper.eq("security_code", request.getCode());
        }
        if (page != null) {
            pagination.setCurrent(page);
        }
        if (limit != null) {
            pagination.setSize(limit);
        }
        return Mono.create(iPageMonoSink ->
                iPageMonoSink.success(subDtoMapper.selectPage(pagination, queryWrapper)));
    }

    @GetMapping("/refresh")
    public Mono<ResponseEntity<String>> getSubInfo() {
        ftQotService.sendSubInfoRequest();
        return Mono.just("commit succeed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

    @PostMapping("/")
    public Mono<ResponseEntity<String>> subscribe(@RequestBody @Validated Mono<SubscribeRequest> request) {
        return Mono.create(responseEntityMonoSink ->
                request.doOnError(WebExchangeBindException.class, throwable -> responseEntityMonoSink.success(new ResponseEntity<>("参数校验失败:" + throwable.getFieldErrors().toString(), HttpStatus.BAD_REQUEST)))
                        .doOnNext(r -> {
                            ftQotService.subscribeRequest(r);
                            responseEntityMonoSink.success(new ResponseEntity<>("subscribe succeed.", HttpStatus.OK));
                        }).subscribe()
        );
    }
}
