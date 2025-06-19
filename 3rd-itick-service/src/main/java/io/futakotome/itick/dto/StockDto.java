package io.futakotome.itick.dto;

import java.util.List;

public class StockDto {
    private Integer code;
    private String msg;
    private List<StockItem> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<StockItem> getData() {
        return data;
    }

    public void setData(List<StockItem> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StockDto{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
