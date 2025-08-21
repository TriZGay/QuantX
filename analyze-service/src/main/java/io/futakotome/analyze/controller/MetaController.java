package io.futakotome.analyze.controller;

import io.futakotome.analyze.biz.DataQuality;
import io.futakotome.analyze.biz.Meta;
import io.futakotome.analyze.biz.backtest.StrategyEnum;
import io.futakotome.analyze.controller.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/meta")
public class MetaController {
    public static final Logger LOGGER = LoggerFactory.getLogger(MetaController.class);
    private static final String PRINT_REQUEST_TEMPLATE = "粒度:{}";
    private final Meta meta;
    private final DataQuality dataQuality;

    public MetaController(Meta meta, DataQuality dataQuality) {
        this.meta = meta;
        this.dataQuality = dataQuality;
    }

    @PostMapping("/dataQaDetails")
    public Mono<ResponseEntity<?>> dataQaDetails(@RequestBody DataQaDetailsRequest request) {
        return Mono.create(responseEntityMonoSink -> {
            try {
                responseEntityMonoSink.success(ResponseEntity.ok(dataQuality.details(request)));
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                responseEntityMonoSink.success(ResponseEntity.internalServerError().body(e.getMessage()));
            }
        });
    }

    @PostMapping("/dataQaPerDay")
    public Mono<ResponseEntity<?>> dataQaPerDay(@RequestBody RangeRequest request) {
        return Mono.create(responseEntityMonoSink -> {
            try {
                responseEntityMonoSink.success(ResponseEntity.ok(dataQuality.qaPerDay(request)));
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                responseEntityMonoSink.success(ResponseEntity.internalServerError().body(e.getMessage()));
            }
        });
    }

    @PostMapping("/truncateTable")
    public Mono<ResponseEntity<?>> truncateTable(@RequestBody TableInfoRequest request) {
        return Mono.create(responseEntityMonoSink -> {
            try {
                responseEntityMonoSink.success(ResponseEntity.ok(meta.truncate(request.getTableName())));
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                responseEntityMonoSink.success(ResponseEntity.internalServerError().body(e.getMessage()));
            }
        });
    }

    @PostMapping("/tableInfo")
    public Mono<ResponseEntity<?>> tableInfo(@RequestBody TableInfoRequest request) {
        return Mono.create(responseEntityMonoSink -> {
            try {
                responseEntityMonoSink.success(ResponseEntity.ok(meta.tableInfo(request.getTableName())));
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                responseEntityMonoSink.success(ResponseEntity.internalServerError().body(e.getMessage()));
            }
        });
    }

    @GetMapping("/dbInfo")
    public Mono<ResponseEntity<?>> dnInfo() {
        return Mono.create(responseEntityMonoSink -> {
            try {
                responseEntityMonoSink.success(ResponseEntity.ok(meta.dbInfo()));
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                responseEntityMonoSink.success(ResponseEntity.internalServerError().body(e.getMessage()));
            }
        });
    }

    @GetMapping("/tables")
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

    @PostMapping("/codes")
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

    @GetMapping("/strategyTypes")
    public Mono<ResponseEntity<?>> getStrategyTypes() {
        return Mono.create(responseEntityMonoSink -> responseEntityMonoSink.success(ResponseEntity.ok(strategyTypesOptions())));
    }

    private List<AntDesignSelectOptions> strategyTypesOptions() {
        return Arrays.stream(StrategyEnum.values()).map(type -> new AntDesignSelectOptions(type.getName(), type.getCode()))
                .collect(Collectors.toList());
    }
}
