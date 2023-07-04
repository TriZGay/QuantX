package io.futakotome.rtck.listener;

import io.futakotome.common.MessageCommon;
import io.futakotome.common.message.RTBasicQuoteMessage;
import io.futakotome.rtck.mapper.RTBasicQuoteMapper;
import io.futakotome.rtck.mapper.dto.RTBasicQuoteDto;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = MessageCommon.RT_BASIC_QUO_CONSUMER_GROUP_STOCK, topic = MessageCommon.RT_BASIC_QUO_TOPIC_STOCK)
public class RTStockBasicQuoteListener implements RocketMQListener<RTBasicQuoteMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RTStockBasicQuoteListener.class);
    private final RTBasicQuoteMapper mapper;

    public RTStockBasicQuoteListener(RTBasicQuoteMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onMessage(RTBasicQuoteMessage rtBasicQuoteMessage) {
        RTBasicQuoteDto dto = new RTBasicQuoteDto();
        dto.setVolume(rtBasicQuoteMessage.getVolume());
        dto.setTurnover(rtBasicQuoteMessage.getTurnover());
        dto.setTurnoverRate(rtBasicQuoteMessage.getTurnoverRate());
        dto.setAmplitude(rtBasicQuoteMessage.getAmplitude());
        dto.setDarkStatus(rtBasicQuoteMessage.getDarkStatus());
        dto.setSecStatus(rtBasicQuoteMessage.getSecStatus());
        dto.setHighPrice(rtBasicQuoteMessage.getHighPrice());
        dto.setMarket(rtBasicQuoteMessage.getMarket());
        dto.setCode(rtBasicQuoteMessage.getCode());
        dto.setPriceSpread(rtBasicQuoteMessage.getPriceSpread());
        dto.setOpenPrice(rtBasicQuoteMessage.getOpenPrice());
        dto.setLowPrice(rtBasicQuoteMessage.getLowPrice());
        dto.setCurPrice(rtBasicQuoteMessage.getCurPrice());
        dto.setLastClosePrice(rtBasicQuoteMessage.getLastClosePrice());
        dto.setUpdateTime(rtBasicQuoteMessage.getUpdateTime());
        if (mapper.insertOneStockBasicQuote(dto)) {
            LOGGER.info("正股实时报价入库成功.");
        }
    }
}
