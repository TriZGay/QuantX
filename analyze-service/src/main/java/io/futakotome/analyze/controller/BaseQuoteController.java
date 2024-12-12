package io.futakotome.analyze.controller;

import io.futakotome.analyze.controller.vo.BaseQuoteRequest;
import io.futakotome.analyze.mapper.BaseQuoteMapper;
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
@RequestMapping("/quote")
public class BaseQuoteController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseQuoteController.class);
    private final BaseQuoteMapper baseQuoteMapper;
    private static final String PRINT_REQUEST_TEMPLATE = "请求参数:{},时间范围:start={},end={}";

    public BaseQuoteController(BaseQuoteMapper baseQuoteMapper) {
        this.baseQuoteMapper = baseQuoteMapper;
    }

    @PostMapping("/list")
    public Mono<ResponseEntity<?>> list(@RequestBody @Validated Mono<BaseQuoteRequest> requestMono) {
        return Mono.create(responseEntityMonoSink ->
                requestMono.doOnError(WebExchangeBindException.class, throwables ->
                        responseEntityMonoSink.success(
                                new ResponseEntity<>("参数校验失败:" + throwables.getFieldErrors(), HttpStatus.BAD_REQUEST)
                        )).doOnNext(baseQuoteRequest -> {
                    LOGGER.info(PRINT_REQUEST_TEMPLATE, baseQuoteRequest.getCode(), baseQuoteRequest.getStart(), baseQuoteRequest.getEnd());
//                    responseEntityMonoSink.success(ResponseEntity.ok(baseQuoteMapper.queryListConditional(baseQuoteRequest)));
                }).subscribe());
    }

}
