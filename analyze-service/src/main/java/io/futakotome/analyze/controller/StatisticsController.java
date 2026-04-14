package io.futakotome.analyze.controller;

import io.futakotome.analyze.biz.Statistics;
import io.futakotome.analyze.mapper.StatisticsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsController.class);
    private final Statistics statistics;

    public StatisticsController(StatisticsMapper statisticsMapper) {
        this.statistics = new Statistics(statisticsMapper);
    }

    @GetMapping("/heatmapByPlates/{market}")
    public Mono<ResponseEntity<?>> heatmapByPlates(@PathVariable("market") Integer market) {
        return Mono.create(responseEntityMonoSink -> {
            try {
                responseEntityMonoSink.success(ResponseEntity.ok(statistics.heatmapByPlates(market)));
            } catch (Exception e) {
                LOGGER.error("请求异常", e);
                responseEntityMonoSink.success(ResponseEntity.internalServerError().body("服务器内部异常"));
            }
        });
    }

}
