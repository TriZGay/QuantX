package io.trizgay.quantx.domain;

public enum BizCommonResultCode {
    QUERY_PLATE_INFO_SUCCESS(10001),
    QUERY_PLATE_INFO_FAILED(10002),
    ERROR(99999);
    private final Integer code;

    BizCommonResultCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
