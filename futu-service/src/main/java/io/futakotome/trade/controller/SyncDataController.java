package io.futakotome.trade.controller;

import io.futakotome.trade.controller.vo.SyncCapitalFlowRequest;
import io.futakotome.trade.service.FTQotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sync")
public class SyncDataController {
    private final FTQotService FTQotService;

    public SyncDataController(FTQotService FTQotService) {
        this.FTQotService = FTQotService;
    }

    @GetMapping("/tradeDate")
    public Mono<ResponseEntity<String>> syncTradeDate() {
        FTQotService.syncTradeDate();
        return Mono.just("commit completed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

    @GetMapping("/stocks")
    public Mono<ResponseEntity<String>> syncStaticInfo() {
        FTQotService.syncStaticInfo();
        return Mono.just("commit completed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

    @GetMapping("/plates")
    public Mono<ResponseEntity<String>> syncPlatesInfo() {
        FTQotService.syncStockOwnerPlateInfo();
        return Mono.just("commit completed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

    @GetMapping("/ipo")
    public Mono<ResponseEntity<String>> syncIpoInfo() {
        FTQotService.syncIpoInfo();
        return Mono.just("commit completed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

    @PostMapping("/capitalFlow")
    public Mono<ResponseEntity<String>> syncCapitalFlow(@RequestBody @Validated Mono<SyncCapitalFlowRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, throwable -> responseEntityMonoSink.success(new ResponseEntity<>("参数校验出错:" + throwable.getFieldErrors(), HttpStatus.BAD_REQUEST)))
                    .doOnNext(r -> {
                        FTQotService.syncCapitalFlow(r);
                        responseEntityMonoSink.success(ResponseEntity.ok("同步资金流向数据成功"));
                    }).subscribe();
        });
    }

}
