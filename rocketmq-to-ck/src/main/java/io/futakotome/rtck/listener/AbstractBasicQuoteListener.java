package io.futakotome.rtck.listener;

import io.futakotome.common.message.RTBasicQuoteMessage;
import io.futakotome.rtck.mapper.dto.RTBasicQuoteDto;
import io.futakotome.rtck.message.core.WebSocketSender;
import io.futakotome.rtck.message.dto.RealTimeBaseQuoteMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractBasicQuoteListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBasicQuoteListener.class);

    protected void sendBasicQuoteWsMessage(RTBasicQuoteMessage rtBasicQuoteMessage, WebSocketSender sender) {
        RealTimeBaseQuoteMessage realTimeBaseQuoteMessage = new RealTimeBaseQuoteMessage();
        realTimeBaseQuoteMessage.setMarket(rtBasicQuoteMessage.getMarket());
        realTimeBaseQuoteMessage.setCode(rtBasicQuoteMessage.getCode());
        realTimeBaseQuoteMessage.setPriceSpread(rtBasicQuoteMessage.getPriceSpread());
        realTimeBaseQuoteMessage.setUpdateTime(rtBasicQuoteMessage.getUpdateTime());
        realTimeBaseQuoteMessage.setHighPrice(rtBasicQuoteMessage.getHighPrice());
        realTimeBaseQuoteMessage.setOpenPrice(rtBasicQuoteMessage.getOpenPrice());
        realTimeBaseQuoteMessage.setLowPrice(rtBasicQuoteMessage.getLowPrice());
        realTimeBaseQuoteMessage.setCurPrice(rtBasicQuoteMessage.getCurPrice());
        realTimeBaseQuoteMessage.setLastClosePrice(rtBasicQuoteMessage.getLastClosePrice());
        realTimeBaseQuoteMessage.setVolume(rtBasicQuoteMessage.getVolume());
        realTimeBaseQuoteMessage.setTurnover(rtBasicQuoteMessage.getTurnover());
        realTimeBaseQuoteMessage.setTurnoverRate(rtBasicQuoteMessage.getTurnoverRate());
        realTimeBaseQuoteMessage.setAmplitude(rtBasicQuoteMessage.getAmplitude());
        realTimeBaseQuoteMessage.setDarkStatus(rtBasicQuoteMessage.getDarkStatus());
        realTimeBaseQuoteMessage.setSecStatus(rtBasicQuoteMessage.getSecStatus());
        if (sender != null) {
            LOGGER.info("WebSocket消息:{}", realTimeBaseQuoteMessage.toString());
            sender.sendData(realTimeBaseQuoteMessage);
        }
    }

    protected RTBasicQuoteDto message2Dto(RTBasicQuoteMessage rtBasicQuoteMessage) {
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
        return dto;
    }
}
