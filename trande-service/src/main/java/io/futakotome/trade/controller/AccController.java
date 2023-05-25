package io.futakotome.trade.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.futakotome.trade.dto.AccDto;
import io.futakotome.trade.dto.AccInfoDto;
import io.futakotome.trade.mapper.AccDtoMapper;
import io.futakotome.trade.mapper.AccInfoDtoMapper;
import io.futakotome.trade.service.QuotesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/acc")
public class AccController {
    private final QuotesService quotesService;
    private final AccDtoMapper accDtoMapper;
    private final AccInfoDtoMapper accInfoDtoMapper;

    public AccController(QuotesService quotesService, AccDtoMapper accDtoMapper, AccInfoDtoMapper accInfoDtoMapper) {
        this.quotesService = quotesService;
        this.accDtoMapper = accDtoMapper;
        this.accInfoDtoMapper = accInfoDtoMapper;
    }

    @GetMapping("/refresh")
    public Mono<ResponseEntity<String>> refreshAccInfo() {
        quotesService.sendGetAccListRequest();
        return Mono.just("commit succeed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

    @GetMapping("/refreshAllFunds")
    public Mono<ResponseEntity<String>> refreshAllAccFunds() {
        quotesService.sendGetFundsRequest();
        return Mono.just("commit succeed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

    @GetMapping("/refreshAllPosition")
    public Mono<ResponseEntity<String>> refreshAllPosition() {
        quotesService.sendGetPositionRequest();
        return Mono.just("commit succeed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

    @PostMapping("/unlock")
    public Mono<ResponseEntity<String>> unlock(@RequestBody @Validated Mono<UnlockRequest> unlockRequest) {
        return Mono.create(responseEntityMonoSink ->
                unlockRequest.doOnError(WebExchangeBindException.class, throwable -> responseEntityMonoSink.success(new ResponseEntity<>("参数校验失败:" + throwable.getFieldErrors().toString(), HttpStatus.BAD_REQUEST)))
                        .doOnNext(r -> {
                            quotesService.sendUnLockRequest(r);
                            responseEntityMonoSink.success(new ResponseEntity<>("commit succeed.", HttpStatus.OK));
                        }).subscribe());
    }

    @GetMapping("/accounts")
    public Mono<ResponseEntity<List<AccDto>>> fetchAccounts() {
        return Mono.create(responseEntityMonoSink ->
                responseEntityMonoSink.success(new ResponseEntity<>(accDtoMapper.selectList(null),
                        HttpStatus.OK)));
    }

    @GetMapping("/info/{accId}")
    public Mono<ResponseEntity<List<AccInfoDto>>> fetchInfoByAccId(@PathVariable("accId") String accId) {
        return Mono.create(responseEntityMonoSink -> {
            QueryWrapper<AccInfoDto> queryWrapper = Wrappers.query();
            queryWrapper.eq("acc_id", accId);
            responseEntityMonoSink.success(new ResponseEntity<>(accInfoDtoMapper.selectList(queryWrapper), HttpStatus.OK));
        });
    }
}
