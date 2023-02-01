package io.trizgay.quantx.ft;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(publicConverter = false, generateConverter = true)
public class FTCommonResult {
    private Integer seqNo;
    private String message;

    public FTCommonResult(JsonObject jsonObject) {
        FTCommonResultConverter.fromJson(jsonObject, this);
    }

    public FTCommonResult(Integer seqNo, String message) {
        this.seqNo = seqNo;
        this.message = message;
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        FTCommonResultConverter.toJson(this, jsonObject);
        return jsonObject;
    }

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "FTCommonResult{" +
                "seqNo=" + seqNo +
                ", message='" + message + '\'' +
                '}';
    }
}
