package io.futakotome.analyze.controller;

import io.futakotome.analyze.biz.Macd;
import io.futakotome.analyze.controller.vo.Granularity;
import io.futakotome.analyze.controller.vo.MacdRequest;
import io.futakotome.analyze.mapper.MacdMapper;
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
@RequestMapping("/macd")
public class MacdController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MaNController.class);
    private static final String PRINT_REQUEST_TEMPLATE = "MACD数据查询,请求参数:{},粒度:{},时间范围:start={},end={}";

    private final Macd macd;

    public MacdController(MacdMapper macdMapper) {
        this.macd = new Macd(macdMapper);
    }

    @PostMapping("/macd12269")
    public Mono<ResponseEntity<?>> macd12269(@RequestBody @Validated Mono<MacdRequest> macdRequestMono) {
        return Mono.create(responseEntityMonoSink -> {
            macdRequestMono.doOnError(WebExchangeBindException.class, throwable -> {
                responseEntityMonoSink.success(new ResponseEntity<>("参数校验失败:" + throwable.getFieldErrors(), HttpStatus.BAD_REQUEST));
            }).doOnError(Exception.class, throwable -> {
                responseEntityMonoSink.success(ResponseEntity.internalServerError().body("服务器内部异常"));
            }).doOnNext(macdRequest -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, macdRequest.getCode(), Granularity.mapName(macdRequest.getGranularity()), macdRequest.getStart(), macdRequest.getEnd());
                try {
                    responseEntityMonoSink.success(ResponseEntity.ok(macd.queryMacdList(macdRequest)));
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                    responseEntityMonoSink.success(ResponseEntity.internalServerError().body(e.getMessage()));
                }
            }).subscribe();
        });
    }
}
