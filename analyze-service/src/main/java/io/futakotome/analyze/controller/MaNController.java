package io.futakotome.analyze.controller;

import io.futakotome.analyze.controller.vo.Granularity;
import io.futakotome.analyze.controller.vo.MaRequest;
import io.futakotome.analyze.domain.MaN;
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
@RequestMapping("/ma")
public class MaNController {
    private static final Logger LOGGER = LoggerFactory.getLogger(Min1KMaNController.class);
    private static final String PRINT_REQUEST_TEMPLATE = "请求参数:{},粒度:{},时间范围:start={},end={}";

    private final MaN ma;

    public MaNController(MaN maN) {
        this.ma = maN;
    }

    @PostMapping("/n")
    public Mono<ResponseEntity<?>> maN(@RequestBody @Validated Mono<MaRequest> maRequestMono) {
        return Mono.create(responseEntityMonoSink -> {
            maRequestMono.doOnError(WebExchangeBindException.class, throwbales -> {
                responseEntityMonoSink.success(new ResponseEntity<>("参数校验失败:" + throwbales.getFieldErrors(), HttpStatus.BAD_REQUEST));
            }).doOnNext(maRequest -> {
                LOGGER.info(PRINT_REQUEST_TEMPLATE, maRequest.getCode(), Granularity.mapName(maRequest.getGranularity()), maRequest.getStart(), maRequest.getEnd());
                responseEntityMonoSink.success(ResponseEntity.ok(ma.maNDataUseArc(maRequest)));
            }).subscribe();
        });
    }
}
