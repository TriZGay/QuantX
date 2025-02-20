package io.futakotome.trade.domain;

import io.futakotome.trade.dto.*;

import java.util.List;

public class Snapshot {
    List<SnapshotBaseDto> baseDtoList;
    List<SnapshotEquityExDto> equityExDtoList;
    List<SnapshotOptionExDto> optionExDtoList;
    List<SnapshotFutureExDto> futureExDtoList;
    List<SnapshotIndexExDto> indexExDtoList;
    List<SnapshotPlateExDto> plateExDtoList;
    List<SnapshotTrustExDto> trustExDtoList;
    List<SnapshotWarrantExDto> warrantExDtoList;

    public List<SnapshotBaseDto> getBaseDtoList() {
        return baseDtoList;
    }

    public void setBaseDtoList(List<SnapshotBaseDto> baseDtoList) {
        this.baseDtoList = baseDtoList;
    }

    public List<SnapshotEquityExDto> getEquityExDtoList() {
        return equityExDtoList;
    }

    public void setEquityExDtoList(List<SnapshotEquityExDto> equityExDtoList) {
        this.equityExDtoList = equityExDtoList;
    }

    public List<SnapshotOptionExDto> getOptionExDtoList() {
        return optionExDtoList;
    }

    public void setOptionExDtoList(List<SnapshotOptionExDto> optionExDtoList) {
        this.optionExDtoList = optionExDtoList;
    }

    public List<SnapshotFutureExDto> getFutureExDtoList() {
        return futureExDtoList;
    }

    public void setFutureExDtoList(List<SnapshotFutureExDto> futureExDtoList) {
        this.futureExDtoList = futureExDtoList;
    }

    public List<SnapshotIndexExDto> getIndexExDtoList() {
        return indexExDtoList;
    }

    public void setIndexExDtoList(List<SnapshotIndexExDto> indexExDtoList) {
        this.indexExDtoList = indexExDtoList;
    }

    public List<SnapshotPlateExDto> getPlateExDtoList() {
        return plateExDtoList;
    }

    public void setPlateExDtoList(List<SnapshotPlateExDto> plateExDtoList) {
        this.plateExDtoList = plateExDtoList;
    }

    public List<SnapshotTrustExDto> getTrustExDtoList() {
        return trustExDtoList;
    }

    public void setTrustExDtoList(List<SnapshotTrustExDto> trustExDtoList) {
        this.trustExDtoList = trustExDtoList;
    }

    public List<SnapshotWarrantExDto> getWarrantExDtoList() {
        return warrantExDtoList;
    }

    public void setWarrantExDtoList(List<SnapshotWarrantExDto> warrantExDtoList) {
        this.warrantExDtoList = warrantExDtoList;
    }
}
