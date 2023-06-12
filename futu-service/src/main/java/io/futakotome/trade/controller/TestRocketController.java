package io.futakotome.trade.controller;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/test")
public class TestRocketController {
    private final RocketMQTemplate rocketMQTemplate;

    public TestRocketController(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    @GetMapping("/send")
    public String testRocket() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "小白");
        map.put("age", 22);
        map.put("sex", "男");
        rocketMQTemplate.convertAndSend("topic-produce", map);
        return "success";

    }
}
