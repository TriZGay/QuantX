package io.futakotome.sec.controller.vo;

public class CommonResult {
    private Integer code;
    private String msg;
    private Object data;

    public static final Integer COMMON_SUCCESS = 10001;
    public static final Integer COMMON_FAILED = 20001;
    public static final Integer SERVER_EXCEPTION = 99999;

    public CommonResult(Builder builder) {
        this.code = builder.code;
        this.msg = builder.msg;
        this.data = builder.data;
    }

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static class Builder {
        private Integer code;
        private String msg;
        private Object data;

        public Builder code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public Builder() {
        }

        public CommonResult build() {
            return new CommonResult(this);
        }
    }
}
