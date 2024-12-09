package io.futakotome.analyze.controller;

import io.futakotome.analyze.biz.KLine;
import io.futakotome.analyze.controller.vo.KLineArchiveRequest;
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
@RequestMapping("/trans")
public class DataTransferController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataTransferController.class);
    private static final String PRINT_REQUEST_TEMPLATE = "表格:{}.时间范围:{}-{}.";
    private final KLine kLine;

    public DataTransferController(KLineMapper mapper) {
        this.kLine = new KLine(mapper);
    }

    @PostMapping("/klineArchive")
    public Mono<ResponseEntity<?>> klineArchive(@RequestBody @Validated Mono<KLineArchiveRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, throwable -> {
                responseEntityMonoSink.success(new ResponseEntity<>("参数校验失败:" + throwable.getFieldErrors(), HttpStatus.BAD_REQUEST));
            }).doOnError(Exception.class, throwable -> {
                responseEntityMonoSink.success(ResponseEntity.internalServerError().body("服务器内部异常"));
            }).doOnNext(request -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, request.getTableName(), request.getStart(), request.getEnd());
                try {
                    responseEntityMonoSink.success(ResponseEntity.ok("数据归档条数:" + kLine.kLinesArchive(request)));
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                    responseEntityMonoSink.success(ResponseEntity.internalServerError().body(e.getMessage()));
                }
            }).subscribe();
        });

    }

}
