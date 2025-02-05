package io.futakotome.rtck.listener;

import io.futakotome.common.message.RTKLMessage;
import io.futakotome.rtck.mapper.dto.RTKLDto;

public abstract class AbstractKLineListener {

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
        dto.setAddTime(rtklMessage.getAddTime());
        return dto;
    }
}
