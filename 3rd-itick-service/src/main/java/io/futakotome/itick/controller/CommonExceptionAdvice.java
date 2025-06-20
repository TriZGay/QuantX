package io.futakotome.itick.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonExceptionAdvice.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionReturn(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
}
