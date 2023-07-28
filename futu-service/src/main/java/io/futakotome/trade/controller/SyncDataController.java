package io.futakotome.trade.controller;

import io.futakotome.trade.service.FTQotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sync")
public class SyncDataController {
    private final FTQotService FTQotService;

    public SyncDataController(FTQotService FTQotService) {
        this.FTQotService = FTQotService;
    }

    @GetMapping("/tradeDate")
    public Mono<ResponseEntity<String>> syncTradeDate() {
        FTQotService.syncTradeDate();
        return Mono.just("commit completed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

    @GetMapping("/stocks")
    public Mono<ResponseEntity<String>> syncStaticInfo() {
        FTQotService.syncStaticInfo();
        return Mono.just("commit completed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

    @GetMapping("/plates")
    public Mono<ResponseEntity<String>> syncPlatesInfo() {
        FTQotService.syncStockOwnerPlateInfo();
        return Mono.just("commit completed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

    @GetMapping("/ipo")
    public Mono<ResponseEntity<String>> syncIpoInfo() {
        FTQotService.syncIpoInfo();
        return Mono.just("commit completed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

}
