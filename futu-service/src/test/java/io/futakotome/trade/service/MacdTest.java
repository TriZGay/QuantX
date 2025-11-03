package io.futakotome.trade.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.futakotome.common.indies.macd.Macd;
import io.futakotome.common.indies.macd.MacdCalculator;
import io.futakotome.trade.dto.KLineDayArcDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MacdTest {
    @Autowired
    private KLineDayArcService kLineDayArcService;

    @Test
    public void testMacd() {
        List<KLineDayArcDto> result = kLineDayArcService.list(Wrappers.query(new KLineDayArcDto())
                .eq("code", "000001").eq("rehab_type", 1)
                .ge("update_time", "2025-01-01")
                .last("limit 200"));
        MacdCalculator macdCalculator = new MacdCalculator();
        result.forEach(k -> {
            Macd macdValue = macdCalculator.compute(k.getClosePrice());
            System.out.println("时间=>" + k.getUpdateTime() + ",close_price=>" + k.getClosePrice() +
                    ",dif=>" + macdValue.getDif() + ",dea=>" + macdValue.getDea() + ",macd=>" + macdValue.getMacd());
        });
    }
}
