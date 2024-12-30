package io.futakotome.rtck.event;

import io.futakotome.rtck.mapper.dto.RTKLDto;

public class KLineReceivedEvent {
    private String tableName;
    private RTKLDto dto;

    public RTKLDto getDto() {
        return dto;
    }

    public void setDto(RTKLDto dto) {
        this.dto = dto;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "KLineReceivedEvent{" +
                "tableName='" + tableName + '\'' +
                ", dto=" + dto.getCode() + ":" + dto.getRehabType() +
                '}';
    }
}
