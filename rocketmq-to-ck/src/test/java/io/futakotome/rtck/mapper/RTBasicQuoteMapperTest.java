package io.futakotome.rtck.mapper;

import io.futakotome.rtck.mapper.dto.RTBasicQuoteDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
class RTBasicQuoteMapperTest {

    @Autowired
    private RTBasicQuoteMapper mapper;

    @Test
    void insertOne() {
        RTBasicQuoteDto dto = new RTBasicQuoteDto();
        dto.setHighPrice(19547.58);
        dto.setMarket(21);
        dto.setCode("000001");
//        dto.setIsSuspended(1);
//        dto.setListTime("1970-01-01");
        dto.setPriceSpread(0.01);
        dto.setOpenPrice(19547.58);
        dto.setLowPrice(19547.58);
        dto.setCurPrice(19547.58);
        dto.setLastClosePrice(3246.1364);
        dto.setUpdateTime("2023-06-14 09:52:17.260");
        assertTrue(mapper.insertOneIndexBasicQuote(dto));
    }
}