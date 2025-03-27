package io.futakotome.analyze.controller;

import io.futakotome.analyze.controller.vo.BackTestRequest;
import io.futakotome.analyze.controller.vo.Granularity;
import io.futakotome.analyze.service.BackTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/backtest")
public class BackTestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BackTestController.class);
    private static final String PRINT_REQUEST_TEMPLATE = "回测请求参数,标的物:{},复权类型:{}时间范围:start={},end={}";
    private final BackTestService backTestService;

    public BackTestController(BackTestService backTestService) {
        this.backTestService = backTestService;
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<?>> addBackTest(@RequestBody @Validated Mono<BackTestRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, throwable -> {
                responseEntityMonoSink.success(new ResponseEntity<>("参数校验失败:" + throwable.getFieldErrors(), HttpStatus.BAD_REQUEST));
            }).doOnError(Exception.class, throwable -> {
                responseEntityMonoSink.success(ResponseEntity.internalServerError().body("服务器内部异常"));
            }).doOnNext(backTestRequest -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, backTestRequest.getCode(), backTestRequest.getRehabType(), backTestRequest.getStart(), backTestRequest.getEnd());
                try {
                    responseEntityMonoSink.success(ResponseEntity.ok(backTestService.backTest(backTestRequest)));
                } catch (Exception e) {
                    responseEntityMonoSink.success(ResponseEntity.internalServerError().body(e.getMessage()));
                }
            }).subscribe();
        });
    }
}
