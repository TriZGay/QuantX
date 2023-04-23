package io.futakotome.sub.controller;

import io.futakotome.sub.service.QuotesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/global")
public class GlobalMarketController {
    private final QuotesService quotesService;

    public GlobalMarketController(QuotesService quotesService) {
        this.quotesService = quotesService;
    }

    @GetMapping("/")
    public Mono<ResponseEntity<String>> getGlobalMarketState() {
        quotesService.sendGlobalMarketStateRequest();
        return Mono.just("commit succeed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }
}
