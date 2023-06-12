package io.futakotome.trade.controller;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RocketMQMessageListener(consumerGroup = "consumer-group", topic = "topic-produce")
public class TestRocketMqListener implements RocketMQListener<HashMap<String, Object>> {
    @Override
    public void onMessage(HashMap<String, Object> map) {
        String name = map.get("name").toString();
        String age = map.get("age").toString();
        String sex = map.get("sex").toString();
        System.out.println("name: " + name + ",age: " + age + ",sex: " + sex);
    }
}
