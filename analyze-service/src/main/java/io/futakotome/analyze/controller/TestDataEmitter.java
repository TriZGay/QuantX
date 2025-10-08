package io.futakotome.analyze.controller;

import io.futakotome.analyze.biz.KLine;
import io.futakotome.analyze.controller.vo.KLineRequest;
import io.futakotome.analyze.controller.vo.KLineResponse;
import io.futakotome.analyze.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
public class TestDataEmitter {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestDataEmitter.class);
    private final KLine kLine;

    public TestDataEmitter(KLine kLine) {
        this.kLine = kLine;
    }

    private KLineRequest wrapRequest(String code, Integer rehabType, Integer granularity, String date) {
        KLineRequest kLineRequest = new KLineRequest();
        kLineRequest.setRehabType(rehabType);
        kLineRequest.setCode(code);
        kLineRequest.setGranularity(granularity);
        kLineRequest.setStart(DateUtils.hkTradeDateStart(date));
        kLineRequest.setEnd(DateUtils.hkTradeDateEnd(date));
        return kLineRequest;
    }

    @GetMapping("/data-emitter/code/{code}/rehab_type/{rehabType}/granularity/{granularity}/date/{date}")
    public Flux<ServerSentEvent<KLineResponse>> streamSse(@PathVariable String code,
                                                          @PathVariable Integer rehabType,
                                                          @PathVariable Integer granularity,
                                                          @PathVariable String date) {
        List<KLineResponse> kLineResponses = kLine.kLinesUseArc(wrapRequest(code, rehabType, granularity, date));
        Sinks.Many<ServerSentEvent<KLineResponse>> sink = Sinks.many().unicast().onBackpressureError();
        Flux<ServerSentEvent<KLineResponse>> flux = sink.asFlux();
        Scheduler single = Schedulers.boundedElastic();
        single.schedule(() -> {
            try {
                for (int i = 0; i < kLineResponses.size(); i++) {
                    ServerSentEvent<KLineResponse> sse = ServerSentEvent.<KLineResponse>builder()
                            .id(String.valueOf(i))
                            .event("kl-test-event")
                            .data(kLineResponses.get(i))
                            .build();
                    LOGGER.info("stream-sse-sink:{}", sse);
                    if (sink.tryEmitNext(sse).isFailure()) {
                        LOGGER.error("sink.tryEmitNext() isFailure");
                        break;
                    }
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                sink.tryEmitError(e);
            } finally {
                sink.tryEmitComplete();
            }
        }, 3, TimeUnit.SECONDS);
        return flux;
    }
}
