package io.futakotome.analyze.controller;

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
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/k")
public class KLineController {
    private static final Logger LOGGER = LoggerFactory.getLogger(KLineController.class);
    private static final String PRINT_REQUEST_TEMPLATE = "请求参数:{},时间范围:start={},end={}";
    private final KLineMapper mapper;

    public KLineController(KLineMapper mapper) {
        this.mapper = mapper;
    }

    @PostMapping("/min15K")
    public Mono<ResponseEntity<?>> min15K(@RequestBody @Validated Mono<KLineRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, throwables ->
                    responseEntityMonoSink.success(
                            new ResponseEntity<>("参数校验失败:" + throwables.getFieldErrors(), HttpStatus.BAD_REQUEST)
                    )).doOnNext(request -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, request.getCode(), request.getStart(), request.getEnd());
                responseEntityMonoSink.success(
                        ResponseEntity.ok(mapper.queryMin15KConditional(request))
                );
            }).subscribe();
        });
    }

    @PostMapping("/dayK")
    public Mono<ResponseEntity<?>> dayK(@RequestBody @Validated Mono<KLineRequest> requestMono) {
        return Mono.create(responseEntityMonoSink ->
                requestMono.doOnError(WebExchangeBindException.class, throwables ->
                        responseEntityMonoSink.success(
                                new ResponseEntity<>("参数校验失败:" + throwables.getFieldErrors(), HttpStatus.BAD_REQUEST)
                        )).doOnNext(request -> {
                    LOGGER.info(PRINT_REQUEST_TEMPLATE, request.toString(), request.getStart(), request.getEnd());
                    responseEntityMonoSink.success(ResponseEntity.ok(mapper.queryDayKConditional(request)));
                }).subscribe());
    }
}
