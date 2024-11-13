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

    @PostMapping("/yearK")
    public Mono<ResponseEntity<?>> yearK(@RequestBody @Validated Mono<KLineRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, throwables ->
                    responseEntityMonoSink.success(
                            new ResponseEntity<>("参数校验失败:" + throwables.getFieldErrors(), HttpStatus.BAD_REQUEST)
                    )).doOnNext(request -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, request.getCode(), request.getStart(), request.getEnd());
                responseEntityMonoSink.success(
                        ResponseEntity.ok(mapper.queryKLineCommon(request, KLineMapper.KL_YEAR_TABLE_NAME))
                );
            }).subscribe();
        });
    }

    @PostMapping("/quarterK")
    public Mono<ResponseEntity<?>> quarterK(@RequestBody @Validated Mono<KLineRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, throwables ->
                    responseEntityMonoSink.success(
                            new ResponseEntity<>("参数校验失败:" + throwables.getFieldErrors(), HttpStatus.BAD_REQUEST)
                    )).doOnNext(request -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, request.getCode(), request.getStart(), request.getEnd());
                responseEntityMonoSink.success(
                        ResponseEntity.ok(mapper.queryKLineCommon(request, KLineMapper.KL_QUARTER_TABLE_NAME))
                );
            }).subscribe();
        });
    }

    @PostMapping("/monthK")
    public Mono<ResponseEntity<?>> monthK(@RequestBody @Validated Mono<KLineRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, throwables ->
                    responseEntityMonoSink.success(
                            new ResponseEntity<>("参数校验失败:" + throwables.getFieldErrors(), HttpStatus.BAD_REQUEST)
                    )).doOnNext(request -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, request.getCode(), request.getStart(), request.getEnd());
                responseEntityMonoSink.success(
                        ResponseEntity.ok(mapper.queryKLineCommon(request, KLineMapper.KL_MONTH_TABLE_NAME))
                );
            }).subscribe();
        });
    }

    @PostMapping("/weekK")
    public Mono<ResponseEntity<?>> weekK(@RequestBody @Validated Mono<KLineRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, throwables ->
                    responseEntityMonoSink.success(
                            new ResponseEntity<>("参数校验失败:" + throwables.getFieldErrors(), HttpStatus.BAD_REQUEST)
                    )).doOnNext(request -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, request.getCode(), request.getStart(), request.getEnd());
                responseEntityMonoSink.success(
                        ResponseEntity.ok(mapper.queryKLineCommon(request, KLineMapper.KL_WEEK_TABLE_NAME))
                );
            }).subscribe();
        });
    }

    @PostMapping("/min60K")
    public Mono<ResponseEntity<?>> min60K(@RequestBody @Validated Mono<KLineRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, throwables ->
                    responseEntityMonoSink.success(
                            new ResponseEntity<>("参数校验失败:" + throwables.getFieldErrors(), HttpStatus.BAD_REQUEST)
                    )).doOnNext(request -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, request.getCode(), request.getStart(), request.getEnd());
                responseEntityMonoSink.success(
                        ResponseEntity.ok(mapper.queryKLineCommon(request, KLineMapper.KL_MIN_60_TABLE_NAME))
                );
            }).subscribe();
        });
    }

    @PostMapping("/min30K")
    public Mono<ResponseEntity<?>> min30K(@RequestBody @Validated Mono<KLineRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, throwables ->
                    responseEntityMonoSink.success(
                            new ResponseEntity<>("参数校验失败:" + throwables.getFieldErrors(), HttpStatus.BAD_REQUEST)
                    )).doOnNext(request -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, request.getCode(), request.getStart(), request.getEnd());
                responseEntityMonoSink.success(
                        ResponseEntity.ok(mapper.queryKLineCommon(request, KLineMapper.KL_MIN_30_TABLE_NAME))
                );
            }).subscribe();
        });
    }

    @PostMapping("/min5K")
    public Mono<ResponseEntity<?>> min5K(@RequestBody @Validated Mono<KLineRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, throwables ->
                    responseEntityMonoSink.success(
                            new ResponseEntity<>("参数校验失败:" + throwables.getFieldErrors(), HttpStatus.BAD_REQUEST)
                    )).doOnNext(request -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, request.getCode(), request.getStart(), request.getEnd());
                responseEntityMonoSink.success(
                        ResponseEntity.ok(mapper.queryKLineCommon(request, KLineMapper.KL_MIN_5_TABLE_NAME))
                );
            }).subscribe();
        });
    }

    @PostMapping("/min3K")
    public Mono<ResponseEntity<?>> min3K(@RequestBody @Validated Mono<KLineRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, throwables ->
                    responseEntityMonoSink.success(
                            new ResponseEntity<>("参数校验失败:" + throwables.getFieldErrors(), HttpStatus.BAD_REQUEST)
                    )).doOnNext(request -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, request.getCode(), request.getStart(), request.getEnd());
                responseEntityMonoSink.success(
                        ResponseEntity.ok(mapper.queryKLineCommon(request, KLineMapper.KL_MIN_3_TABLE_NAME))
                );
            }).subscribe();
        });
    }

    @PostMapping("/min1K")
    public Mono<ResponseEntity<?>> min1K(@RequestBody @Validated Mono<KLineRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, throwables ->
                    responseEntityMonoSink.success(
                            new ResponseEntity<>("参数校验失败:" + throwables.getFieldErrors(), HttpStatus.BAD_REQUEST)
                    )).doOnNext(request -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, request.getCode(), request.getStart(), request.getEnd());
                responseEntityMonoSink.success(
                        ResponseEntity.ok(mapper.queryKLineArchived(request, KLineMapper.KL_MIN_1_ARC_TABLE_NAME))
                );
            }).subscribe();
        });
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
                        ResponseEntity.ok(mapper.queryKLineCommon(request, KLineMapper.KL_MIN_15_TABLE_NAME))
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
                    responseEntityMonoSink.success(ResponseEntity.ok(mapper.queryKLineCommon(request, KLineMapper.KL_DAY_TABLE_NAME)));
                }).subscribe());
    }
}
