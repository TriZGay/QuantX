package io.futakotome.quantx.collect.domain.vo;

import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FTResponse {
    private Integer retType;
    private String retMsg;
    private Integer errCode;
    private Map<String, Object> s2c;

    public Integer getRetType() {
        return retType;
    }

    public void setRetType(Integer retType) {
        this.retType = retType;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public Map<String, Object> getS2c() {
        return s2c;
    }

    public void setS2c(Map<String, Object> s2c) {
        this.s2c = s2c;
    }

    public List<PlateInfo> parseToPlateInfoList() {
        JsonObject s2c = new JsonObject(this.s2c);
        if (s2c.containsKey("plateInfoList")) {
            return s2c.getJsonArray("plateInfoList")
                    .stream()
                    .map(JsonObject::mapFrom)
                    .map(jsonObject -> jsonObject.mapTo(PlateInfo.class))
                    .collect(Collectors.toList());
        } else {
            throw new IllegalStateException("GetPlateSetFailed: `plateInfoList` not in the rsp. ");
        }
    }

    @Override
    public String toString() {
        return "FTResponse{" +
                "retType=" + retType +
                ", retMsg='" + retMsg + '\'' +
                ", errCode=" + errCode +
                ", s2c='" + s2c + '\'' +
                '}';
    }
}
