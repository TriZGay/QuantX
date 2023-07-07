package io.futakotome.analyze.controller.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BaseQuoteRequest extends RangeRequest {
    @NotNull(message = "code必填")
    @NotEmpty(message = "code不能为空")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
