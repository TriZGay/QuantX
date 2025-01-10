package io.futakotome.analyze.controller;

import io.futakotome.analyze.biz.Boll;
import io.futakotome.analyze.controller.vo.BollRequest;
import io.futakotome.analyze.controller.vo.Granularity;
import io.futakotome.analyze.mapper.BollMapper;
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
@RequestMapping("/boll")
public class BollController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MaNController.class);
    private static final String PRINT_REQUEST_TEMPLATE = "MA数据查询,请求参数:{},时间范围:start={},end={}";
    private final Boll boll;

    public BollController(BollMapper bollMapper) {
        this.boll = new Boll(bollMapper);
    }

    @PostMapping("/boll2002")
    public Mono<ResponseEntity<?>> boll2002(@RequestBody @Validated Mono<BollRequest> bollRequestMono) {
        return Mono.create(responseEntityMonoSink -> {
            bollRequestMono.doOnError(WebExchangeBindException.class, throwable -> {
                responseEntityMonoSink.success(new ResponseEntity<>("参数校验失败:" + throwable.getFieldErrors(), HttpStatus.BAD_REQUEST));
            }).doOnError(Exception.class, throwable -> {
                responseEntityMonoSink.success(ResponseEntity.internalServerError().body("服务器内部异常"));
            }).doOnNext(bollRequest -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, bollRequest.getCode(), bollRequest.getStart(), bollRequest.getEnd());
                try {
                    responseEntityMonoSink.success(ResponseEntity.ok(boll.list(bollRequest)));
                } catch (Exception e) {
                    responseEntityMonoSink.success(ResponseEntity.internalServerError().body(e.getMessage()));
                }
            }).subscribe();
        });
    }
}
