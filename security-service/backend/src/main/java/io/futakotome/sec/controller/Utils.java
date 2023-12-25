package io.futakotome.sec.controller;

import io.futakotome.sec.controller.vo.CommonResult;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.MonoSink;

import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class Utils {
    public static Consumer<? super WebExchangeBindException> badRequestHandle(MonoSink<ResponseEntity<CommonResult>> responseEntityMonoSink) {
        return (throwable) -> {
            responseEntityMonoSink.success(ResponseEntity.badRequest().body(new CommonResult.Builder()
                    .code(CommonResult.SERVER_EXCEPTION)
                    .msg(throwable.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .collect(Collectors.joining()))
                    .build()));
        };
    }

    public static void success(MonoSink<ResponseEntity<CommonResult>> responseEntityMonoSink, Integer code, String msg) {
        responseEntityMonoSink.success(ResponseEntity.ok(new CommonResult.Builder()
                .code(code)
                .msg(msg)
                .build()));
    }

    public static void successWithData(MonoSink<ResponseEntity<CommonResult>> responseEntityMonoSink, Integer code, String msg, Object data) {
        responseEntityMonoSink.success(ResponseEntity.ok(new CommonResult.Builder()
                .code(code)
                .msg(msg)
                .data(data)
                .build()));
    }
}
