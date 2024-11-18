package io.futakotome.analyze.controller;

import io.futakotome.analyze.biz.KLine;
import io.futakotome.analyze.controller.vo.Granularity;
import io.futakotome.analyze.controller.vo.KLineRequest;
import io.futakotome.analyze.mapper.KLineMapper;
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
@RequestMapping("/k")
public class KLineController {
    private static final Logger LOGGER = LoggerFactory.getLogger(KLineController.class);
    private static final String PRINT_REQUEST_TEMPLATE = "请求参数:{},粒度:{},时间范围:start={},end={}";
    private final KLine kLine;

    public KLineController(KLineMapper mapper) {
        this.kLine = new KLine(mapper);
    }

    @PostMapping("/n")
    public Mono<ResponseEntity<?>> yearK(@RequestBody @Validated Mono<KLineRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, throwable -> {
                responseEntityMonoSink.success(new ResponseEntity<>("参数校验失败:" + throwable.getFieldErrors(), HttpStatus.BAD_REQUEST));
            }).doOnError(Exception.class, throwable -> {
                responseEntityMonoSink.success(ResponseEntity.internalServerError().body("服务器内部异常"));
            }).doOnNext(request -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, request.getCode(), Granularity.mapName(request.getGranularity()), request.getStart(), request.getEnd());
                try {
                    responseEntityMonoSink.success(ResponseEntity.ok(kLine.kLinesUseArc(request)));
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                    responseEntityMonoSink.success(ResponseEntity.internalServerError().body(e.getMessage()));
                }
            }).subscribe();
        });
    }
}
