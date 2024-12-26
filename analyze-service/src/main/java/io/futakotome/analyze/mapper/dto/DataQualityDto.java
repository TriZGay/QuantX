package io.futakotome.analyze.mapper.dto;

import java.time.LocalDate;

public class DataQualityDto {
    private Long id;
    private LocalDate checkDate;
    private boolean klineHasRepeat;

    public DataQualityDto() {
    }

    public DataQualityDto(LocalDate checkDate, boolean klineHasRepeat) {
        this.checkDate = checkDate;
        this.klineHasRepeat = klineHasRepeat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(LocalDate checkDate) {
        this.checkDate = checkDate;
    }

    public boolean isKlineHasRepeat() {
        return klineHasRepeat;
    }

    public void setKlineHasRepeat(boolean klineHasRepeat) {
        this.klineHasRepeat = klineHasRepeat;
    }
}
