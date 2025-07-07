package io.futakotome.akshares.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fetch-stocks")
public class StockController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);

    @PostMapping("/")
    public ResponseEntity<?> fetchStocks() {
        return ResponseEntity.ok("hello");
    }
}
