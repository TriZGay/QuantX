package io.futakotome.stock.controller;

import io.futakotome.stock.service.QuotesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sync")
public class SyncDataController {
    private final QuotesService quotesService;

    public SyncDataController(QuotesService quotesService) {
        this.quotesService = quotesService;
    }

    @GetMapping("/stocks")
    public Mono<ResponseEntity<String>> syncStaticInfo() {
        quotesService.syncStaticInfo();
        return Mono.just("commit completed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

    @GetMapping("/plates")
    public Mono<ResponseEntity<String>> syncPlatesInfo() {
        quotesService.syncStockOwnerPlateInfo();
        return Mono.just("commit completed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

    @GetMapping("/ipo")
    public Mono<ResponseEntity<String>> syncIpoInfo() {
        quotesService.syncIpoInfo();
        return Mono.just("commit completed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

}
