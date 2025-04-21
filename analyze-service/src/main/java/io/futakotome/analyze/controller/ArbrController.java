package io.futakotome.analyze.controller;

import io.futakotome.analyze.biz.Arbr;
import io.futakotome.analyze.controller.vo.ArbrRequest;
import io.futakotome.analyze.controller.vo.Granularity;
import io.futakotome.analyze.mapper.ArbrMapper;
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
@RequestMapping("/arbr")
public class ArbrController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArbrController.class);
    private static final String PRINT_REQUEST_TEMPLATE = "ARBR数据查询,请求参数:{},粒度:{},时间范围:start={},end={}";
    private final Arbr arbr;

    public ArbrController(ArbrMapper arbrMapper) {
        this.arbr = new Arbr(arbrMapper);
    }

    @PostMapping("/arbr26")
    public Mono<ResponseEntity<?>> boll2002(@RequestBody @Validated Mono<ArbrRequest> arbrRequestMono) {
        return Mono.create(responseEntityMonoSink -> {
            arbrRequestMono.doOnError(WebExchangeBindException.class, throwable -> {
                responseEntityMonoSink.success(new ResponseEntity<>("参数校验失败:" + throwable.getFieldErrors(), HttpStatus.BAD_REQUEST));
            }).doOnError(Exception.class, throwable -> {
                responseEntityMonoSink.success(ResponseEntity.internalServerError().body("服务器内部异常"));
            }).doOnNext(arbrRequest -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, arbrRequest.getCode(), Granularity.mapName(arbrRequest.getGranularity()), arbrRequest.getStart(), arbrRequest.getEnd());
                try {
                    responseEntityMonoSink.success(ResponseEntity.ok(arbr.list(arbrRequest)));
                } catch (Exception e) {
                    responseEntityMonoSink.success(ResponseEntity.internalServerError().body(e.getMessage()));
                }
            }).subscribe();
        });
    }
}
