package io.futakotome.trade.controller;

import io.futakotome.trade.domain.code.KLType;
import io.futakotome.trade.dto.message.KLMessageContent;
import io.futakotome.trade.service.KafkaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testKafka")
public class TestKafkaController {
    private final KafkaService kafkaService;

    public TestKafkaController(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @GetMapping("/sendKL")
    public ResponseEntity<String> sendKL() {
        for (int i = 0; i < 10; i++) {
            KLMessageContent klMessageContent = new KLMessageContent();
            klMessageContent.setRehabType(0);
            klMessageContent.setMarket(1);
            klMessageContent.setCode("ddddd");
            klMessageContent.setTime("2025-03-04 12:00:00");
            klMessageContent.setHighPrice(0D);
            klMessageContent.setOpenPrice(0D);
            klMessageContent.setLowPrice(0D);
            klMessageContent.setClosePrice(0D);
            klMessageContent.setLastClosePrice(0D);
            klMessageContent.setVolume(0L);
            klMessageContent.setTurnover(0D);
            klMessageContent.setTurnoverRate(0D);
            klMessageContent.setPe(0D);
            klMessageContent.setChangeRate(0D);
            klMessageContent.setTimestamp(0D);
            klMessageContent.setBlank(false);
            klMessageContent.setKlType(KLType.MIN_1.getCode());
            kafkaService.sendRTKLineMessage(klMessageContent);
        }
        for (int i = 0; i < 10; i++) {
            KLMessageContent klMessageContent = new KLMessageContent();
            klMessageContent.setRehabType(2);
            klMessageContent.setMarket(1);
            klMessageContent.setCode("ddddd");
            klMessageContent.setTime("2025-03-04 12:00:00");
            klMessageContent.setHighPrice(0D);
            klMessageContent.setOpenPrice(0D);
            klMessageContent.setLowPrice(0D);
            klMessageContent.setClosePrice(0D);
            klMessageContent.setLastClosePrice(0D);
            klMessageContent.setVolume(0L);
            klMessageContent.setTurnover(0D);
            klMessageContent.setTurnoverRate(0D);
            klMessageContent.setPe(0D);
            klMessageContent.setChangeRate(0D);
            klMessageContent.setTimestamp(0D);
            klMessageContent.setBlank(false);
            klMessageContent.setKlType(KLType.MIN_1.getCode());
            kafkaService.sendRTKLineMessage(klMessageContent);
        }
        for (int i = 0; i < 10; i++) {
            KLMessageContent klMessageContent = new KLMessageContent();
            klMessageContent.setRehabType(1);
            klMessageContent.setMarket(1);
            klMessageContent.setCode("ddddd");
            klMessageContent.setTime("2025-03-04 12:00:00");
            klMessageContent.setHighPrice(0D);
            klMessageContent.setOpenPrice(0D);
            klMessageContent.setLowPrice(0D);
            klMessageContent.setClosePrice(0D);
            klMessageContent.setLastClosePrice(0D);
            klMessageContent.setVolume(0L);
            klMessageContent.setTurnover(0D);
            klMessageContent.setTurnoverRate(0D);
            klMessageContent.setPe(0D);
            klMessageContent.setChangeRate(0D);
            klMessageContent.setTimestamp(0D);
            klMessageContent.setBlank(false);
            klMessageContent.setKlType(KLType.MIN_1.getCode());
            kafkaService.sendRTKLineMessage(klMessageContent);
        }
        return ResponseEntity.ok("yes!");
    }
}
