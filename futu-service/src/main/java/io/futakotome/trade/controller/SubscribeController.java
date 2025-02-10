package io.futakotome.trade.controller;

import io.futakotome.trade.controller.vo.ListSubscribeRequest;
import io.futakotome.trade.service.SubDtoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sub")
public class SubscribeController {
    private final SubDtoService subService;

    public SubscribeController(SubDtoService subService) {
        this.subService = subService;
    }

    @PostMapping("/list")
    public ResponseEntity<?> listSubscribe(@RequestBody ListSubscribeRequest request) {
        return ResponseEntity.ok(subService.findList(request));
    }

    @PostMapping("/details")
    public ResponseEntity<?> listSubscribeDetails(@RequestBody ListSubscribeRequest request) {
        return ResponseEntity.ok(subService.findDetails(request));
    }

}
