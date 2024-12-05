package io.futakotome.analyze.controller;

import io.futakotome.analyze.biz.Meta;
import io.futakotome.analyze.controller.vo.Granularity;
import io.futakotome.analyze.controller.vo.MetaRequest;
import io.futakotome.analyze.mapper.MetaDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/meta")
public class MetaController {
    public static final Logger LOGGER = LoggerFactory.getLogger(MetaController.class);
    private static final String PRINT_REQUEST_TEMPLATE = "粒度:{}";
    private final Meta meta;

    public MetaController(MetaDataMapper mapper) {
        this.meta = new Meta(mapper);
    }

    @GetMapping(value = "/tables")
    public Mono<ResponseEntity<?>> getTables() {
        return Mono.create(responseEntityMonoSink -> {
            try {
                responseEntityMonoSink.success(ResponseEntity.ok(meta.showTables()));
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                responseEntityMonoSink.success(ResponseEntity.internalServerError().body(e.getMessage()));
            }
        });
    }

    @PostMapping(value = "/codes")
    public Mono<ResponseEntity<?>> getMeta(@RequestBody @Validated Mono<MetaRequest> metaRequest) {
        return Mono.create(responseEntityMonoSink -> {
            metaRequest.doOnError(WebExchangeBindException.class, throwable -> {
                responseEntityMonoSink.success(new ResponseEntity<>("参数校验失败:" + throwable.getFieldErrors(), HttpStatus.BAD_REQUEST));
            }).doOnError(Exception.class, throwable -> {
                responseEntityMonoSink.success(ResponseEntity.internalServerError().body("服务器内部异常"));
            }).doOnNext(request -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, Granularity.mapName(request.getGranularity()));
                try {
                    responseEntityMonoSink.success(ResponseEntity.ok(meta.hasDataCodes(request)));
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                    responseEntityMonoSink.success(ResponseEntity.internalServerError().body(e.getMessage()));
                }
            }).subscribe();
        });
    }
}
