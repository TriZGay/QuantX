package io.futakotome.analyze.controller;

import io.futakotome.analyze.controller.vo.MaRequest;
import io.futakotome.analyze.mapper.MaNMapper;
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
@RequestMapping("/dayk")
public class DayKMaNController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DayKMaNController.class);
    private static final String PRINT_REQUEST_TEMPLATE = "请求参数:{},时间范围:start={},end={}";

    private final MaNMapper mapper;

    public DayKMaNController(MaNMapper mapper) {
        this.mapper = mapper;
    }

    @PostMapping("/ma5")
    public Mono<ResponseEntity<?>> dayKMa5(@RequestBody @Validated Mono<MaRequest> maRequestMono) {
        return Mono.create(responseEntityMonoSink -> {
            maRequestMono.doOnError(WebExchangeBindException.class, throwbales -> {
                responseEntityMonoSink.success(
                        new ResponseEntity<>("参数校验失败:" + throwbales.getFieldErrors(), HttpStatus.BAD_REQUEST)
                );
            }).doOnNext(maRequest -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, maRequest.getCode(), maRequest.getStart(), maRequest.getEnd());
                responseEntityMonoSink.success(
                        ResponseEntity.ok(mapper.queryDayMaNCommon(maRequest, MaNMapper.DAY_K_MA5_TABLE_NAME))
                );
            }).subscribe();
        });
    }

    @PostMapping("/ma10")
    public Mono<ResponseEntity<?>> dayKMa10(@RequestBody @Validated Mono<MaRequest> maRequestMono) {
        return Mono.create(responseEntityMonoSink -> {
            maRequestMono.doOnError(WebExchangeBindException.class, throwbales -> {
                responseEntityMonoSink.success(
                        new ResponseEntity<>("参数校验失败:" + throwbales.getFieldErrors(), HttpStatus.BAD_REQUEST)
                );
            }).doOnNext(maRequest -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, maRequest.getCode(), maRequest.getStart(), maRequest.getEnd());
                responseEntityMonoSink.success(
                        ResponseEntity.ok(mapper.queryDayMaNCommon(maRequest, MaNMapper.DAY_K_MA10_TABLE_NAME))
                );
            }).subscribe();
        });
    }

    @PostMapping("/ma20")
    public Mono<ResponseEntity<?>> dayKMa20(@RequestBody @Validated Mono<MaRequest> maRequestMono) {
        return Mono.create(responseEntityMonoSink -> {
            maRequestMono.doOnError(WebExchangeBindException.class, throwbales -> {
                responseEntityMonoSink.success(
                        new ResponseEntity<>("参数校验失败:" + throwbales.getFieldErrors(), HttpStatus.BAD_REQUEST)
                );
            }).doOnNext(maRequest -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, maRequest.getCode(), maRequest.getStart(), maRequest.getEnd());
                responseEntityMonoSink.success(
                        ResponseEntity.ok(mapper.queryDayMaNCommon(maRequest, MaNMapper.DAY_K_MA20_TABLE_NAME))
                );
            }).subscribe();
        });
    }

    @PostMapping("/ma30")
    public Mono<ResponseEntity<?>> dayKMa30(@RequestBody @Validated Mono<MaRequest> maRequestMono) {
        return Mono.create(responseEntityMonoSink -> {
            maRequestMono.doOnError(WebExchangeBindException.class, throwbales -> {
                responseEntityMonoSink.success(
                        new ResponseEntity<>("参数校验失败:" + throwbales.getFieldErrors(), HttpStatus.BAD_REQUEST)
                );
            }).doOnNext(maRequest -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, maRequest.getCode(), maRequest.getStart(), maRequest.getEnd());
                responseEntityMonoSink.success(
                        ResponseEntity.ok(mapper.queryDayMaNCommon(maRequest, MaNMapper.DAY_K_MA30_TABLE_NAME))
                );
            }).subscribe();
        });
    }
}
