package io.futakotome.trade.controller.vo;

public class SnapshotResponse {
    private SnapshotBaseResponse baseResponse;
    private SnapshotEquityResponse equityResponse;
    private SnapshotPlateResponse plateResponse;

    public SnapshotPlateResponse getPlateResponse() {
        return plateResponse;
    }

    public void setPlateResponse(SnapshotPlateResponse plateResponse) {
        this.plateResponse = plateResponse;
    }

    public SnapshotBaseResponse getBaseResponse() {
        return baseResponse;
    }

    public void setBaseResponse(SnapshotBaseResponse baseResponse) {
        this.baseResponse = baseResponse;
    }

    public SnapshotEquityResponse getEquityResponse() {
        return equityResponse;
    }

    public void setEquityResponse(SnapshotEquityResponse equityResponse) {
        this.equityResponse = equityResponse;
    }
}
