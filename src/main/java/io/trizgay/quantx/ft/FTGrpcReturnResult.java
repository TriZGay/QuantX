package io.trizgay.quantx.ft;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(publicConverter = false, generateConverter = true)
public class FTGrpcReturnResult {
    private Integer retType;
    private Integer errCode;
    private String retMsg;
    private JsonObject s2c;

    public FTGrpcReturnResult(Integer retType, Integer errCode, String retMsg, JsonObject s2c) {
        this.retType = retType;
        this.errCode = errCode;
        this.retMsg = retMsg;
        this.s2c = s2c;
    }

    public FTGrpcReturnResult(JsonObject jsonObject) {
        FTGrpcReturnResultConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        FTGrpcReturnResultConverter.toJson(this, jsonObject);
        return jsonObject;
    }

    public Integer getRetType() {
        return retType;
    }

    public void setRetType(Integer retType) {
        this.retType = retType;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public JsonObject getS2c() {
        return s2c;
    }

    public void setS2c(JsonObject s2c) {
        this.s2c = s2c;
    }
}
