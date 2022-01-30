package io.trizgay.quantx.domain;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(publicConverter = false, generateConverter = true)
public class BizCommonResult {
    private Integer code;
    private String message;
    private String payload;

    public BizCommonResult(BizCommonResultCode bizCommonResultCode, String message, String payload) {
        this.code = bizCommonResultCode.getCode();
        this.message = message;
        this.payload = payload;
    }

    public BizCommonResult(BizCommonResultCode bizCommonResultCode, String message) {
        this.code = bizCommonResultCode.getCode();
        this.message = message;
    }

    public BizCommonResult(JsonObject jsonObject) {
        BizCommonResultConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        BizCommonResultConverter.toJson(this, jsonObject);
        return jsonObject;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "BizCommonResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }
}
