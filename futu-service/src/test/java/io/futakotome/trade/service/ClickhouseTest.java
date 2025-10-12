package io.futakotome.trade.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.futakotome.trade.dto.KLineMin1ArcDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClickhouseTest {

    @Autowired
    private KLineMin1ArcService kLineMin1ArcService;

    @Test
    public void getKLineMin1Arc() {
        List<KLineMin1ArcDto> result = kLineMin1ArcService.list(Wrappers.query(new KLineMin1ArcDto())
                .eq("code", "00700").eq("rehab_type", 1)
                .ge("update_time", "2025-09-01").last("limit 10"));
        System.out.println(result);
    }

    @Test
    public void insertBatchKLineMin1Arc() {
        KLineMin1ArcDto one = new KLineMin1ArcDto();
        one.setMarket(1);
        one.setCode("test");
        one.setRehabType(1);
        one.setHighPrice(1D);
        one.setOpenPrice(1D);
        one.setLowPrice(1D);
        one.setClosePrice(1D);
        one.setLastClosePrice(1D);
        one.setVolume(1L);
        one.setTurnover(1D);
        one.setTurnoverRate(1D);
        one.setPe(1D);
        one.setChangeRate(1D);
        one.setUpdateTime("2025-10-11 09:30:12");
        KLineMin1ArcDto two = new KLineMin1ArcDto();
        two.setMarket(1);
        two.setCode("test");
        two.setRehabType(1);
        two.setHighPrice(1D);
        two.setOpenPrice(1D);
        two.setLowPrice(1D);
        two.setClosePrice(1D);
        two.setLastClosePrice(1D);
        two.setVolume(1L);
        two.setTurnover(1D);
        two.setTurnoverRate(1D);
        two.setPe(1D);
        two.setChangeRate(1D);
        two.setUpdateTime("2025-10-11 09:31:12");
        List<KLineMin1ArcDto> toAdd = Arrays.asList(one, two);
        boolean result = kLineMin1ArcService.saveBatch(toAdd);
        System.out.println(result);
    }
}
