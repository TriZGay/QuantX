//package io.futakotome.trade.controller;
//
//import io.futakotome.trade.controller.vo.OrderRequest;
//import io.futakotome.trade.service.FTTradeService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.support.WebExchangeBindException;
//import reactor.core.publisher.Mono;
//
//
//@RestController
//@RequestMapping("/trade")
//public class TradeController {
//
//    private final FTTradeService FTTradeService;
//
//    public TradeController(FTTradeService FTTradeService) {
//        this.FTTradeService = FTTradeService;
//    }
//
//    @PostMapping("/order")
//    public Mono<ResponseEntity<String>> order(@RequestBody @Validated Mono<OrderRequest> orderRequest) {
//        return Mono.create(responseEntityMonoSink ->
//                orderRequest.doOnError(WebExchangeBindException.class, throwable -> responseEntityMonoSink.success(new ResponseEntity<>("参数校验失败:" + throwable.getFieldErrors().toString(), HttpStatus.BAD_REQUEST)))
//                        .doOnNext(request -> {
//                            FTTradeService.sendOrderRequest(request);
//                            responseEntityMonoSink.success(new ResponseEntity<>("order commit succeed.", HttpStatus.OK));
//                        }).subscribe());
//    }
//
//    @PostMapping("/refreshOrder")
//    public Mono<ResponseEntity<String>> refreshOrder() {
//        FTTradeService.sendGetTodayOrderListRequest();
//        FTTradeService.sendGetHistoryOrderListRequest();
//        return Mono.just("commit succeed.")
//                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
//    }
//}
