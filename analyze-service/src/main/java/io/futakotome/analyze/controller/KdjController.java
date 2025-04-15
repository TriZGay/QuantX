package io.futakotome.analyze.controller;

import io.futakotome.analyze.biz.Boll;
import io.futakotome.analyze.biz.Kdj;
import io.futakotome.analyze.controller.vo.BollRequest;
import io.futakotome.analyze.controller.vo.Granularity;
import io.futakotome.analyze.controller.vo.KdjRequest;
import io.futakotome.analyze.mapper.BollMapper;
import io.futakotome.analyze.mapper.KdjMapper;
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
@RequestMapping("/kdj")
public class KdjController {
    private static final Logger LOGGER = LoggerFactory.getLogger(KdjController.class);
    private static final String PRINT_REQUEST_TEMPLATE = "KDJ数据查询,请求参数:{},粒度:{},时间范围:start={},end={}";
    private final Kdj kdj;

    public KdjController(KdjMapper kdjMapper) {
        this.kdj = new Kdj(kdjMapper);
    }

    @PostMapping("/kdj933")
    public Mono<ResponseEntity<?>> boll2002(@RequestBody @Validated Mono<KdjRequest> kdjRequestMono) {
        return Mono.create(responseEntityMonoSink -> {
            kdjRequestMono.doOnError(WebExchangeBindException.class, throwable -> {
                responseEntityMonoSink.success(new ResponseEntity<>("参数校验失败:" + throwable.getFieldErrors(), HttpStatus.BAD_REQUEST));
            }).doOnError(Exception.class, throwable -> {
                responseEntityMonoSink.success(ResponseEntity.internalServerError().body("服务器内部异常"));
            }).doOnNext(kdjRequest -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, kdjRequest.getCode(), Granularity.mapName(kdjRequest.getGranularity()), kdjRequest.getStart(), kdjRequest.getEnd());
                try {
                    responseEntityMonoSink.success(ResponseEntity.ok(kdj.queryKdjList(kdjRequest)));
                } catch (Exception e) {
                    responseEntityMonoSink.success(ResponseEntity.internalServerError().body(e.getMessage()));
                }
            }).subscribe();
        });
    }
}
