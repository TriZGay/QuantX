package io.futakotome.sub.controller;

import io.futakotome.sub.service.QuotesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sub")
public class SubscribeController {
    private final QuotesService quotesService;

    public SubscribeController(QuotesService quotesService) {
        this.quotesService = quotesService;
    }

    @GetMapping("/refresh")
    public Mono<ResponseEntity<String>> getSubInfo() {
        quotesService.sendSubInfoRequest();
        return Mono.just("commit succeed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

    @PostMapping("/")
    public Mono<ResponseEntity<String>> subscribe(@RequestBody @Validated Mono<SubscribeRequest> request) {
        return Mono.create(responseEntityMonoSink ->
                request.doOnError(WebExchangeBindException.class, throwable -> responseEntityMonoSink.success(new ResponseEntity<>("参数校验失败:" + throwable.getFieldErrors().toString(), HttpStatus.BAD_REQUEST)))
                        .doOnNext(r -> {
                            quotesService.subscribeRequest(r);
                            responseEntityMonoSink.success(new ResponseEntity<>("subscribe succeed.", HttpStatus.OK));
                        }).subscribe()
        );
    }
}
