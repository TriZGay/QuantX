package io.futakotome.trade.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.futakotome.trade.controller.vo.PositionListRequest;
import io.futakotome.trade.controller.vo.UnlockRequest;
import io.futakotome.trade.dto.AccDto;
import io.futakotome.trade.dto.AccInfoDto;
import io.futakotome.trade.dto.PositionDto;
import io.futakotome.trade.mapper.AccDtoMapper;
import io.futakotome.trade.mapper.AccInfoDtoMapper;
import io.futakotome.trade.mapper.PositionDtoMapper;
import io.futakotome.trade.service.FTTradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(AccController.class);
    private static final String PRINT_REQUEST_TEMPLATE = "{}请求参数:{},分页参数:current[{}],size[{}]";
    private final FTTradeService ftTradeService;
    private final AccDtoMapper accDtoMapper;
    private final AccInfoDtoMapper accInfoDtoMapper;
    private final PositionDtoMapper positionDtoMapper;

    public AccController(FTTradeService ftTradeService, AccDtoMapper accDtoMapper, AccInfoDtoMapper accInfoDtoMapper, PositionDtoMapper positionDtoMapper) {
        this.ftTradeService = ftTradeService;
        this.accDtoMapper = accDtoMapper;
        this.accInfoDtoMapper = accInfoDtoMapper;
        this.positionDtoMapper = positionDtoMapper;
    }

    @GetMapping("/refresh")
    public Mono<ResponseEntity<String>> refreshAccInfo() {
        ftTradeService.sendGetAccListRequest();
        return Mono.just("commit succeed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

    @GetMapping("/refreshAllFunds")
    public Mono<ResponseEntity<String>> refreshAllAccFunds() {
        ftTradeService.sendGetFundsRequest();
        return Mono.just("commit succeed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

    @GetMapping("/refreshAllPosition")
    public Mono<ResponseEntity<String>> refreshAllPosition() {
        ftTradeService.sendGetPositionRequest();
        return Mono.just("commit succeed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

    @PostMapping("/positions")
    public Mono<IPage<PositionDto>> positions(@RequestBody PositionListRequest request) {
        LOGGER.info(PRINT_REQUEST_TEMPLATE, "查询持仓", request.toString(), request.getCurrent(), request.getSize());
        QueryWrapper<PositionDto> queryWrapper = Wrappers.query();
        Page<PositionDto> pagination = Page.of(1, 10);
        if (request.getCurrent() != null) {
            pagination.setCurrent(request.getCurrent());
        }
        if (request.getSize() != null) {
            pagination.setSize(request.getSize());
        }
        return Mono.create(iPageMonoSink ->
                iPageMonoSink.success(positionDtoMapper.selectPage(pagination, queryWrapper)));
    }

    @PostMapping("/unlock")
    public Mono<ResponseEntity<String>> unlock(@RequestBody @Validated Mono<UnlockRequest> unlockRequest) {
        return Mono.create(responseEntityMonoSink ->
                unlockRequest.doOnError(WebExchangeBindException.class, throwable -> responseEntityMonoSink.success(new ResponseEntity<>("参数校验失败:" + throwable.getFieldErrors().toString(), HttpStatus.BAD_REQUEST)))
                        .doOnNext(r -> {
                            ftTradeService.sendUnLockRequest(r);
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
