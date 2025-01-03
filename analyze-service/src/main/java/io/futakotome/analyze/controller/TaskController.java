package io.futakotome.analyze.controller;

import io.futakotome.analyze.controller.vo.JobRequest;
import io.futakotome.analyze.controller.vo.KLineRaw2ArcJobRequest;
import io.futakotome.analyze.controller.vo.KLineRepeatCheckJobRequest;
import io.futakotome.analyze.job.KLineRaw2ArcJob;
import io.futakotome.analyze.job.KLineRepeatJob;
import io.futakotome.analyze.service.QuartzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequestMapping("/task")
public class TaskController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
    private final QuartzService quartzService;

    public TaskController(QuartzService quartzService) {
        this.quartzService = quartzService;
    }

    @GetMapping("/list")
    public Mono<ResponseEntity<?>> listTask() {
        return Mono.create(responseEntityMonoSink -> {
            try {
                responseEntityMonoSink.success(ResponseEntity.ok(quartzService.getTriggers()));
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                responseEntityMonoSink.success(ResponseEntity.internalServerError().body(e.getMessage()));
            }
        });
    }

    @PostMapping("/del")
    public Mono<ResponseEntity<?>> delTask(@RequestBody @Validated Mono<JobRequest> jobRequestMono) {
        return Mono.create(responseEntityMonoSink -> {
            jobRequestMono.doOnError(WebExchangeBindException.class, throwable -> {
                responseEntityMonoSink.success(new ResponseEntity<>("参数校验失败:" + throwable.getFieldErrors(), HttpStatus.BAD_REQUEST));
            }).doOnError(Exception.class, throwable -> {
                responseEntityMonoSink.success(ResponseEntity.internalServerError().body("服务器内部异常"));
            }).doOnNext(jobRequest -> {
                try {
                    responseEntityMonoSink.success(ResponseEntity.ok(quartzService.delJob(jobRequest.getJobName())));
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                    responseEntityMonoSink.success(ResponseEntity.internalServerError().body(e.getMessage()));
                }
            }).subscribe();
        });
    }

    @PostMapping("/addKLineCheckTask")
    public Mono<ResponseEntity<?>> addKLineCheckTask(@RequestBody @Validated Mono<KLineRepeatCheckJobRequest> requestMono) {
        return Mono.create(responseEntityMonoSink -> {
            requestMono.doOnError(WebExchangeBindException.class, throwable -> {
                responseEntityMonoSink.success(new ResponseEntity<>("参数校验失败:" + throwable.getFieldErrors(), HttpStatus.BAD_REQUEST));
            }).doOnError(Exception.class, throwable -> {
                responseEntityMonoSink.success(ResponseEntity.internalServerError().body("服务器内部异常"));
            }).doOnNext(request -> {
                try {
                    if (Objects.nonNull(request.getCron()) && !request.getCron().isEmpty()) {
                        //定时执行
                        responseEntityMonoSink.success(ResponseEntity.ok(quartzService.addJob(request.getJobName(), request.getCron(),
                                JobRequest.toJobDataMap(request), KLineRepeatJob.class)));
                    } else {
                        //马上执行
                        responseEntityMonoSink.success(ResponseEntity.ok(quartzService.addJob(request.getJobName(),
                                JobRequest.toJobDataMap(request), KLineRepeatJob.class)));
                    }
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                    responseEntityMonoSink.success(ResponseEntity.internalServerError().body(e.getMessage()));
                }
            }).subscribe();
        });
    }

    @PostMapping("/addKLineRaw2ArcTask")
    public Mono<ResponseEntity<?>> addKLineRaw2ArcTask(@RequestBody @Validated Mono<KLineRaw2ArcJobRequest> jobRequestMono) {
        return Mono.create(responseEntityMonoSink -> {
            jobRequestMono.doOnError(WebExchangeBindException.class, throwable -> {
                responseEntityMonoSink.success(new ResponseEntity<>("参数校验失败:" + throwable.getFieldErrors(), HttpStatus.BAD_REQUEST));
            }).doOnError(Exception.class, throwable -> {
                responseEntityMonoSink.success(ResponseEntity.internalServerError().body("服务器内部异常"));
            }).doOnNext(request -> {
                try {
                    if (Objects.nonNull(request.getCron()) && !request.getCron().isEmpty()) {
                        //定时执行
                        responseEntityMonoSink.success(ResponseEntity.ok(quartzService.addJob(request.getJobName(), request.getCron(),
                                JobRequest.toJobDataMap(request), KLineRaw2ArcJob.class)));
                    } else {
                        //马上执行
                        responseEntityMonoSink.success(ResponseEntity.ok(quartzService.addJob(request.getJobName(),
                                JobRequest.toJobDataMap(request), KLineRaw2ArcJob.class)));
                    }
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                    responseEntityMonoSink.success(ResponseEntity.internalServerError().body(e.getMessage()));
                }
            }).subscribe();
        });
    }

}
