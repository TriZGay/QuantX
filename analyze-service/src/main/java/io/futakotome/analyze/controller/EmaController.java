package io.futakotome.analyze.controller;

import io.futakotome.analyze.biz.Ema;
import io.futakotome.analyze.controller.vo.EmaRequest;
import io.futakotome.analyze.controller.vo.Granularity;
import io.futakotome.analyze.mapper.EmaMapper;
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
@RequestMapping("/ema")
public class EmaController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmaController.class);
    private static final String PRINT_REQUEST_TEMPLATE = "EMA数据查询,请求参数:{},粒度:{},时间范围:{}-{}";
    private final Ema ema;

    public EmaController(EmaMapper emaMapper) {
        this.ema = new Ema(emaMapper);
    }

    @PostMapping("/n")
    public Mono<ResponseEntity<?>> emaList(@RequestBody @Validated Mono<EmaRequest> emaRequestMono) {
        return Mono.create(responseEntityMonoSink -> {
            emaRequestMono.doOnError(WebExchangeBindException.class, throwable -> {
                responseEntityMonoSink.success(new ResponseEntity<>("参数校验失败:" + throwable.getFieldErrors(), HttpStatus.BAD_REQUEST));
            }).doOnError(Exception.class, throwable -> {
                responseEntityMonoSink.success(ResponseEntity.internalServerError().body("服务器内部异常"));
            }).doOnNext(emaRequest -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, emaRequest.getCode(), Granularity.mapName(emaRequest.getGranularity()),
                        emaRequest.getStart(), emaRequest.getEnd());
                try {
                    responseEntityMonoSink.success(ResponseEntity.ok(ema.list(emaRequest)));
                } catch (Exception e) {
                    responseEntityMonoSink.success(ResponseEntity.internalServerError().body(e.getMessage()));
                }
            }).subscribe();
        });
    }
}
