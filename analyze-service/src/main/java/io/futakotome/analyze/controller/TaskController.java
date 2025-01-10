package io.futakotome.analyze.controller;

import io.futakotome.analyze.controller.vo.JobRequest;
import io.futakotome.analyze.service.QuartzService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/task")
public class TaskController {
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
                    responseEntityMonoSink.success(ResponseEntity.internalServerError().body(e.getMessage()));
                }
            }).subscribe();
        });
    }

    @PostMapping("/addTask")
    public Mono<ResponseEntity<?>> addTask(@RequestBody @Validated Mono<? extends JobRequest> jobRequestMono) {
        return Mono.create(responseEntityMonoSink -> {
            jobRequestMono.doOnError(WebExchangeBindException.class, throwable -> {
                responseEntityMonoSink.success(new ResponseEntity<>("参数校验失败:" + throwable.getFieldErrors(), HttpStatus.BAD_REQUEST));
            }).doOnError(Exception.class, throwable -> {
                responseEntityMonoSink.success(ResponseEntity.internalServerError().body("服务器内部异常"));
            }).doOnNext(jobRequest -> {
                try {
                    responseEntityMonoSink.success(ResponseEntity.ok(quartzService.addJob(jobRequest)));
                } catch (Exception e) {
                    responseEntityMonoSink.success(ResponseEntity.internalServerError().body(e.getMessage()));
                }
            }).subscribe();
        });
    }
}
