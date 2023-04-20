package io.futakotome.sub.controller;

import io.futakotome.sub.service.QuotesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sub")
public class SubscribeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscribeController.class);

    private final QuotesService quotesService;

    public SubscribeController(QuotesService quotesService) {
        this.quotesService = quotesService;
    }

    @GetMapping("/")
    public Mono<ResponseEntity<String>> getSubInfo() {
        quotesService.sendSubInfoRequest();
        return Mono.just("commit succeed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

    @PostMapping("/")
    public Mono<ResponseEntity<String>> subscribe(@RequestBody SubscribeRequest request) {
        quotesService.subscribeRequest(request);
        return Mono.just("subscribe succeed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }
}
