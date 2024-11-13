package io.futakotome.analyze.controller;

import io.futakotome.analyze.mapper.KLineMapper;
import io.futakotome.analyze.mapper.MetaDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/meta")
public class MetaDataController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MetaDataController.class);
    private final MetaDataMapper metaDataMapper;

    public MetaDataController(MetaDataMapper metaDataMapper) {
        this.metaDataMapper = metaDataMapper;
    }

    @GetMapping("/yearKCodes")
    public Mono<ResponseEntity<List<Object>>> yearKCodes() {
        return Mono.create(responseEntityMonoSink ->
                responseEntityMonoSink.success(ResponseEntity.ok(metaDataMapper.kLineDistinctCodesCommon(KLineMapper.KL_YEAR_TABLE_NAME))));
    }

    @GetMapping("/quarterKCodes")
    public Mono<ResponseEntity<List<Object>>> quarterKCodes() {
        return Mono.create(responseEntityMonoSink ->
                responseEntityMonoSink.success(ResponseEntity.ok(metaDataMapper.kLineDistinctCodesCommon(KLineMapper.KL_QUARTER_TABLE_NAME))));
    }

    @GetMapping("/monthKCodes")
    public Mono<ResponseEntity<List<Object>>> monthKCodes() {
        return Mono.create(responseEntityMonoSink ->
                responseEntityMonoSink.success(ResponseEntity.ok(metaDataMapper.kLineDistinctCodesCommon(KLineMapper.KL_MONTH_TABLE_NAME))));
    }

    @GetMapping("/weekKCodes")
    public Mono<ResponseEntity<List<Object>>> weekKCodes() {
        return Mono.create(responseEntityMonoSink ->
                responseEntityMonoSink.success(ResponseEntity.ok(metaDataMapper.kLineDistinctCodesCommon(KLineMapper.KL_WEEK_TABLE_NAME))));
    }

    @GetMapping("/min60Codes")
    public Mono<ResponseEntity<List<Object>>> min60Codes() {
        return Mono.create(responseEntityMonoSink ->
                responseEntityMonoSink.success(ResponseEntity.ok(metaDataMapper.kLineDistinctCodesCommon(KLineMapper.KL_MIN_60_TABLE_NAME))));
    }

    @GetMapping("/min30Codes")
    public Mono<ResponseEntity<List<Object>>> min30Codes() {
        return Mono.create(responseEntityMonoSink ->
                responseEntityMonoSink.success(ResponseEntity.ok(metaDataMapper.kLineDistinctCodesCommon(KLineMapper.KL_MIN_30_TABLE_NAME))));
    }

    @GetMapping("/min1Codes")
    public Mono<ResponseEntity<List<Object>>> min1Codes() {
        return Mono.create(responseEntityMonoSink ->
                responseEntityMonoSink.success(ResponseEntity.ok(metaDataMapper.kLineDistinctCodesCommon(KLineMapper.KL_MIN_1_TABLE_NAME))));
    }

    @GetMapping("/min3Codes")
    public Mono<ResponseEntity<List<Object>>> min3Codes() {
        return Mono.create(responseEntityMonoSink ->
                responseEntityMonoSink.success(ResponseEntity.ok(metaDataMapper.kLineDistinctCodesCommon(KLineMapper.KL_MIN_3_TABLE_NAME))));
    }

    @GetMapping("/min5Codes")
    public Mono<ResponseEntity<List<Object>>> min5Codes() {
        return Mono.create(responseEntityMonoSink ->
                responseEntityMonoSink.success(ResponseEntity.ok(metaDataMapper.kLineDistinctCodesCommon(KLineMapper.KL_MIN_5_TABLE_NAME))));
    }

    @GetMapping("/min15Codes")
    public Mono<ResponseEntity<List<Object>>> min15Codes() {
        return Mono.create(responseEntityMonoSink ->
                responseEntityMonoSink.success(ResponseEntity.ok(metaDataMapper.kLineDistinctCodesCommon(KLineMapper.KL_MIN_15_TABLE_NAME))));
    }

    @GetMapping("/dayKCodes")
    public Mono<ResponseEntity<List<Object>>> dayKCodes() {
        return Mono.create(responseEntityMonoSink ->
                responseEntityMonoSink.success(ResponseEntity.ok(metaDataMapper.kLineDistinctCodesCommon(KLineMapper.KL_DAY_TABLE_NAME))));
    }

    @GetMapping("/indiesCodes")
    public Mono<ResponseEntity<Map<String, String>>> indiesCodes() {
        return Mono.create(responseEntityMonoSink -> {
            List<String> codes = metaDataMapper.indiesDistinctCodes();
            Map<String, String> kv = new HashMap<>();
            codes.forEach(code -> kv.put(code, code));
            responseEntityMonoSink.success(ResponseEntity.ok(kv));
        });
    }
}
