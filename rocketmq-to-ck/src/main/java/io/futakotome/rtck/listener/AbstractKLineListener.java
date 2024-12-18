package io.futakotome.rtck.listener;

import io.futakotome.common.message.RTKLMessage;
import io.futakotome.rtck.mapper.dto.RTKLDto;
import io.futakotome.rtck.message.core.WebSocketSender;
import io.futakotome.rtck.message.dto.RealTimeKLMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public abstract class AbstractKLineListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractKLineListener.class);

    protected void sendKLineWsMessage(RTKLMessage rtklMessage, WebSocketSender sender) {
        RealTimeKLMessage realTimeKLMessage = new RealTimeKLMessage();
        realTimeKLMessage.setMarket(rtklMessage.getMarket());
        realTimeKLMessage.setCode(rtklMessage.getCode());
        realTimeKLMessage.setRehabType(rtklMessage.getRehabType());
        realTimeKLMessage.setHighPrice(rtklMessage.getHighPrice());
        realTimeKLMessage.setOpenPrice(rtklMessage.getOpenPrice());
        realTimeKLMessage.setLowPrice(rtklMessage.getLowPrice());
        realTimeKLMessage.setClosePrice(rtklMessage.getClosePrice());
        realTimeKLMessage.setLastClosePrice(rtklMessage.getLastClosePrice());
        realTimeKLMessage.setVolume(rtklMessage.getVolume());
        realTimeKLMessage.setTurnover(rtklMessage.getTurnover());
        realTimeKLMessage.setTurnoverRate(rtklMessage.getTurnoverRate());
        realTimeKLMessage.setPe(rtklMessage.getPe());
        realTimeKLMessage.setChangeRate(rtklMessage.getChangeRate());
        realTimeKLMessage.setUpdateTime(rtklMessage.getUpdateTime());
        if (sender != null) {
            LOGGER.info("WebSocket消息:{}", realTimeKLMessage.toString());
            sender.sendData(realTimeKLMessage);
        }
    }

    protected RTKLDto message2Dto(RTKLMessage rtklMessage) {
        RTKLDto dto = new RTKLDto();
        dto.setMarket(rtklMessage.getMarket());
        dto.setCode(rtklMessage.getCode());
        dto.setRehabType(rtklMessage.getRehabType());
        dto.setHighPrice(rtklMessage.getHighPrice());
        dto.setOpenPrice(rtklMessage.getOpenPrice());
        dto.setLowPrice(rtklMessage.getLowPrice());
        dto.setClosePrice(rtklMessage.getClosePrice());
        dto.setLastClosePrice(rtklMessage.getLastClosePrice());
        dto.setVolume(rtklMessage.getVolume());
        dto.setTurnover(rtklMessage.getTurnover());
        dto.setTurnoverRate(rtklMessage.getTurnoverRate());
        dto.setPe(rtklMessage.getPe());
        dto.setChangeRate(rtklMessage.getChangeRate());
        dto.setUpdateTime(rtklMessage.getUpdateTime());
        dto.setAddTime(LocalDateTime.now(ZoneId.of("Asia/Shanghai"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return dto;
    }
}
