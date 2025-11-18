package io.futakotome.trade.test;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.futakotome.trade.domain.code.KLType;
import io.futakotome.trade.dto.KLineMin1ArcDto;
import io.futakotome.trade.dto.KLineMin1RawDto;
import io.futakotome.trade.dto.message.KLMessageContent;
import io.futakotome.trade.service.KLineMin1ArcService;
import io.futakotome.trade.service.KLineMin1RawService;
import io.futakotome.trade.service.KafkaService;
import io.futakotome.trade.service.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static io.futakotome.trade.service.MailService.ME;

@RestController
@RequestMapping("/testKafka")
public class TestKafkaController {
    private final KafkaService kafkaService;
    private final MailService mailService;
    private final KLineMin1RawService kLineMin1RawService;

    public TestKafkaController(KafkaService kafkaService, MailService mailService, KLineMin1RawService kLineMin1RawService) {
        this.kafkaService = kafkaService;
        this.mailService = mailService;
        this.kLineMin1RawService = kLineMin1RawService;
    }

    @GetMapping("/sendMail")
    public ResponseEntity<String> sendMail() {
        mailService.sendSimpleMail(ME, "测试", "测试");
        return ResponseEntity.ok("ok~");
    }

    @GetMapping("/sendKL")
    public ResponseEntity<String> sendKL() {
        List<KLineMin1RawDto> testData = kLineMin1RawService.getBaseMapper().selectList(Wrappers.query(new KLineMin1RawDto())
                .eq("code", "00700")
                .eq("rehab_type", 1)
                .ge("update_time", "2025-11-17 09:30:00")
                .le("update_time", "2025-11-17 16:00:00"));
        this.publishTestData(testData);
        return ResponseEntity.ok("yes!");
    }

    @Async("kafkaPublishTaskExecutor")
    public void publishTestData(List<KLineMin1RawDto> testData) {
        for (KLineMin1RawDto testDatum : testData) {
            try {
                KLMessageContent klMessageContent = new KLMessageContent();
                klMessageContent.setMarket(testDatum.getMarket());
                klMessageContent.setCode(testDatum.getCode());
                klMessageContent.setKlType(1); //实时1分钟
                klMessageContent.setRehabType(testDatum.getRehabType());
                klMessageContent.setTime(testDatum.getUpdateTime());
                klMessageContent.setBlank(false);
                klMessageContent.setHighPrice(testDatum.getHighPrice());
                klMessageContent.setOpenPrice(testDatum.getOpenPrice());
                klMessageContent.setLowPrice(testDatum.getLowPrice());
                klMessageContent.setClosePrice(testDatum.getClosePrice());
                klMessageContent.setLastClosePrice(testDatum.getLastClosePrice());
                klMessageContent.setVolume(testDatum.getVolume());
                klMessageContent.setTurnover(testDatum.getTurnover());
                klMessageContent.setTurnoverRate(testDatum.getTurnoverRate());
                klMessageContent.setPe(testDatum.getPe());
                klMessageContent.setChangeRate(testDatum.getChangeRate());
                kafkaService.sendRTKLineMessage(klMessageContent);
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
