package io.futakotome.itick.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.futakotome.itick.client.ItickHttpClient;
import io.futakotome.itick.controller.vo.SymbolListRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fetch")
public class ItickDataFetchController {
    private final ItickHttpClient itickService;

    public ItickDataFetchController(ItickHttpClient itickService) {
        this.itickService = itickService;
    }

    @PostMapping("/symbol/list")
    public ResponseEntity<?> symbolList(@RequestBody SymbolListRequest request) {
        return ResponseEntity.ok(itickService.fetchProducts(request));
    }
}
