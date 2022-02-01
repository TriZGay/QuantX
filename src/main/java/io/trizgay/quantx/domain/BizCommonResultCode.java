package io.trizgay.quantx.domain;

public enum BizCommonResultCode {
    QUERY_PLATE_INFO_SUCCESS(10001),
    QUERY_PLATE_INFO_FAILED(10002),
    QUERY_IPO_INFO_SUCCESS(20001),
    QUERY_IPO_INFO_FAILED(20002),
    QUERY_SECURITY_SUCCESS(30001),
    QUERY_SECURITY_FAILED(30002),
    ERROR(99999);
    private final Integer code;

    BizCommonResultCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
