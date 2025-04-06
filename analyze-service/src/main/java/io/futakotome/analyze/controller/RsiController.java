package io.futakotome.analyze.controller;

import io.futakotome.analyze.biz.Macd;
import io.futakotome.analyze.biz.Rsi;
import io.futakotome.analyze.controller.vo.Granularity;
import io.futakotome.analyze.controller.vo.MacdRequest;
import io.futakotome.analyze.controller.vo.RsiRequest;
import io.futakotome.analyze.mapper.MacdMapper;
import io.futakotome.analyze.mapper.RsiMapper;
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
@RequestMapping("/rsi")
public class RsiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RsiController.class);
    private static final String PRINT_REQUEST_TEMPLATE = "RSI数据查询,请求参数:{},粒度:{},时间范围:start={},end={}";

    private final Rsi rsi;

    public RsiController(RsiMapper rsiMapper) {
        this.rsi = new Rsi(rsiMapper);
    }

    @PostMapping("/rsi61224")
    public Mono<ResponseEntity<?>> rsi61224(@RequestBody @Validated Mono<RsiRequest> rsiRequestMono) {
        return Mono.create(responseEntityMonoSink -> {
            rsiRequestMono.doOnError(WebExchangeBindException.class, throwable -> {
                responseEntityMonoSink.success(new ResponseEntity<>("参数校验失败:" + throwable.getFieldErrors(), HttpStatus.BAD_REQUEST));
            }).doOnError(Exception.class, throwable -> {
                responseEntityMonoSink.success(ResponseEntity.internalServerError().body("服务器内部异常"));
            }).doOnNext(rsiRequest -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, rsiRequest.getCode(), Granularity.mapName(rsiRequest.getGranularity()), rsiRequest.getStart(), rsiRequest.getEnd());
                try {
                    responseEntityMonoSink.success(ResponseEntity.ok(rsi.queryRsiList(rsiRequest)));
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                    responseEntityMonoSink.success(ResponseEntity.internalServerError().body(e.getMessage()));
                }
            }).subscribe();
        });
    }

}
