package io.futakotome.trade.controller;

import io.futakotome.trade.service.QuotesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/acc")
public class AccController {
    private final QuotesService quotesService;

    public AccController(QuotesService quotesService) {
        this.quotesService = quotesService;
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

}
