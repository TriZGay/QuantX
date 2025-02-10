//package io.futakotome.trade.controller;
//
//import io.futakotome.trade.controller.vo.SyncCapitalDistributionRequest;
//import io.futakotome.trade.controller.vo.SyncCapitalFlowRequest;
//import io.futakotome.trade.controller.vo.SyncHistoryKRequest;
//import io.futakotome.trade.controller.vo.SyncRehabRequest;
//import io.futakotome.trade.service.FTQotService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.support.WebExchangeBindException;
//import reactor.core.publisher.Mono;
//
//@RestController
//@RequestMapping("/sync")
//public class SyncDataController {
//    private final FTQotService FTQotService;
//
//    public SyncDataController(FTQotService FTQotService) {
//        this.FTQotService = FTQotService;
//    }
//
//
//    @GetMapping("/stocks")
//    public Mono<ResponseEntity<String>> syncStaticInfo() {
//        FTQotService.syncStaticInfo();
//        return Mono.just("commit completed.")
//                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
//    }
//
//    @GetMapping("/plates")
//    public Mono<ResponseEntity<String>> syncPlatesInfo() {
//        FTQotService.syncStockOwnerPlateInfo();
//        return Mono.just("commit completed.")
//                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
//    }
//
//    @GetMapping("/ipo")
//    public Mono<ResponseEntity<String>> syncIpoInfo() {
//        FTQotService.syncIpoInfo();
//        return Mono.just("commit completed.")
//                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
//    }
//
//    @PostMapping("/capitalFlow")
//    public Mono<ResponseEntity<String>> syncCapitalFlow(@RequestBody @Validated Mono<SyncCapitalFlowRequest> requestMono) {
//        return Mono.create(responseEntityMonoSink -> {
//            requestMono.doOnError(WebExchangeBindException.class, throwable -> responseEntityMonoSink.success(new ResponseEntity<>("参数校验出错:" + throwable.getFieldErrors(), HttpStatus.BAD_REQUEST)))
//                    .doOnNext(r -> {
//                        FTQotService.syncCapitalFlow(r);
//                        responseEntityMonoSink.success(ResponseEntity.ok("同步资金流向数据成功"));
//                    }).subscribe();
//        });
//    }
//
//    @PostMapping("/capitalDtb")
//    public Mono<ResponseEntity<String>> syncCapitalDistribution(@RequestBody @Validated Mono<SyncCapitalDistributionRequest> requestMono) {
//        return Mono.create(responseEntityMonoSink -> {
//            requestMono.doOnError(WebExchangeBindException.class, throwable -> responseEntityMonoSink.success(new ResponseEntity<>("参数校验出错:" + throwable.getFieldErrors(), HttpStatus.BAD_REQUEST)))
//                    .doOnNext(r -> {
//                        FTQotService.syncCapitalDistribution(r);
//                        responseEntityMonoSink.success(ResponseEntity.ok("同步资金分布数据成功"));
//                    }).subscribe();
//        });
//    }
//
//    @PostMapping("/rehabs")
//    public Mono<ResponseEntity<String>> syncRehabs(@RequestBody @Validated Mono<SyncRehabRequest> requestMono) {
//        return Mono.create(responseEntityMonoSink -> {
//            requestMono.doOnError(WebExchangeBindException.class, throwable -> responseEntityMonoSink.success(new ResponseEntity<>("参数校验出错:" + throwable.getFieldErrors(), HttpStatus.BAD_REQUEST)))
//                    .doOnNext(r -> {
//                        FTQotService.sendRehabRequest(r);
//                        responseEntityMonoSink.success(ResponseEntity.ok("同步复权因子数据成功"));
//                    }).subscribe();
//        });
//    }
//
//    @PostMapping("/historyK")
//    public Mono<ResponseEntity<String>> syncHistoryK(@RequestBody @Validated Mono<SyncHistoryKRequest> requestMono) {
//        return Mono.create(responseEntityMonoSink -> {
//            requestMono.doOnError(WebExchangeBindException.class, throwable -> responseEntityMonoSink.success(new ResponseEntity<>("参数校验出错:" + throwable.getFieldErrors(), HttpStatus.BAD_REQUEST)))
//                    .doOnNext(r -> {
//                        FTQotService.sendHistoryKLineRequest(r);
//                        responseEntityMonoSink.success(ResponseEntity.ok("同步历史K线数据成功"));
//                    }).subscribe();
//        });
//    }
//
//}
