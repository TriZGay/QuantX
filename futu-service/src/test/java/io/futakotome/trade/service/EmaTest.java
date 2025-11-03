package io.futakotome.trade.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.futakotome.common.indies.ema.*;
import io.futakotome.trade.dto.KLineDayArcDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmaTest {

    @Autowired
    private KLineDayArcService kLineDayArcService;

    @Test
    public void testEma() {
        List<KLineDayArcDto> result = kLineDayArcService.list(Wrappers.query(new KLineDayArcDto())
                .eq("code", "000001").eq("rehab_type", 1)
                .ge("update_time", "2025-01-01")
                .last("limit 200"));
        Ema5 ema5 = new Ema5();
        Ema10 ema10 = new Ema10();
        Ema12 ema12 = new Ema12();
        Ema20 ema20 = new Ema20();
        Ema26 ema26 = new Ema26();
        Ema60 ema60 = new Ema60();
        Ema120 ema120 = new Ema120();
        result.forEach(k -> {
            Double ema5Value = ema5.compute(k.getClosePrice());
            Double ema10Value = ema10.compute(k.getClosePrice());
            Double ema12Value = ema12.compute(k.getClosePrice());
            Double ema20Value = ema20.compute(k.getClosePrice());
            Double ema26Value = ema26.compute(k.getClosePrice());
            Double ema60Value = ema60.compute(k.getClosePrice());
            Double ema120Value = ema120.compute(k.getClosePrice());
            System.out.println("时间=>" + k.getUpdateTime() + ",close_price=>" + k.getClosePrice() +
                    ",ema5=>" + ema5Value + ",ema10=>" + ema10Value + ",ema12=>" + ema12Value +
                    ",ema20=>" + ema20Value + ",ema26=>" + ema26Value + ",ema60=>" + ema60Value +
                    ",ema120=>" + ema120Value);
        });
    }
}
