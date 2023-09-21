package io.futakotome.trade.controller;

import io.futakotome.trade.service.FTQotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/global")
public class GlobalMarketController {
    private final FTQotService ftQotService;

    public GlobalMarketController(FTQotService quotesService) {
        this.ftQotService = quotesService;
    }

    @GetMapping("/")
    public Mono<ResponseEntity<String>> getGlobalMarketState() {
        ftQotService.sendGlobalMarketStateRequest();
        return Mono.just("commit succeed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

    @GetMapping("/historyKDetail")
    public Mono<ResponseEntity<String>> getHistoryKLDetail() {
        ftQotService.sendHistoryKLineDetailRequest();
        return Mono.just("commit succeed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

    @GetMapping("/connect")
    public Mono<ResponseEntity<String>> connect2FT() {
        ftQotService.connect();
        return Mono.just("commit succeed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

    @GetMapping("/disconnect")
    public Mono<ResponseEntity<String>> disconnect2FT() {
        ftQotService.disconnect();
        return Mono.just("ocmmit succeed.")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }
}
