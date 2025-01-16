package io.futakotome.analyze.mapper.dto;

import java.util.Objects;

public class CodeAndRehabTypeKey {
    private Integer rehabType;
    private String code;

    public CodeAndRehabTypeKey(Integer rehabType, String code) {
        this.rehabType = rehabType;
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeAndRehabTypeKey that = (CodeAndRehabTypeKey) o;
        return Objects.equals(rehabType, that.rehabType) && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rehabType, code);
    }

    public Integer getRehabType() {
        return rehabType;
    }

    public void setRehabType(Integer rehabType) {
        this.rehabType = rehabType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
