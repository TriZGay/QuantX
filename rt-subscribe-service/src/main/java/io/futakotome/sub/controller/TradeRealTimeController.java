package io.futakotome.sub.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

@Controller
public class TradeRealTimeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TradeRealTimeController.class);
    private final RSocketRequester rSocketRequester;

    public TradeRealTimeController(RSocketRequester.Builder builder) {
        this.rSocketRequester = builder.websocket(URI.create("ws://localhost:9000/rsocket"));
    }

    @GetMapping("/testSteam/{number}")
    public Mono<ResponseEntity<String>> testSteam(@PathVariable Integer number) {
        rSocketRequester.route("number.stream").data(number).retrieveFlux(Integer.class)
                .subscribe(message -> LOGGER.info("客户端收到:" + message));
        return Mono.just("stream")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

    @GetMapping("/testChannel/{number}")
    public Mono<ResponseEntity<String>> testChannel(@PathVariable Integer number) {
        Mono<Integer> setting1 = Mono.just(1);
        Mono<Integer> setting2 = Mono.just(2);
        Flux<Integer> c2s = Flux.concat(setting1, setting2)
                .doOnNext(i -> LOGGER.info("客户端发送:" + i));
        rSocketRequester.route("number.channel").data(c2s).retrieveFlux(Long.class)
                .subscribe(message -> LOGGER.info("客户端收到:" + message));
        return Mono.just("channel")
                .map(str -> new ResponseEntity<>(str, HttpStatus.OK));
    }

    @MessageMapping("number.stream")
    public Flux<Integer> responseStream(Integer number) {
        return Flux.range(1, number)
                .delayElements(Duration.ofSeconds(1));
    }

    @MessageMapping("number.channel")
    public Flux<Long> biDirectionStream(Flux<Long> numberFlux) {
        return numberFlux
                .map(n -> n * n)
                .onErrorReturn(-1L);
    }

}
