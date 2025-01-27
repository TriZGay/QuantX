package io.futakotome.analyze.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTest {
    @Autowired
    private KieContainer kieContainer;

    @Test
    public void testDrools() {
        KieSession kieSession = kieContainer.newKieSession();
        Order order = new Order();
        order.setAmount(1300);
        kieSession.insert(order);
        kieSession.fireAllRules();
        kieSession.dispose();
        System.out.println("订单金额：" + order.getAmount() +
                "，添加积分：" + order.getScore());
    }
}