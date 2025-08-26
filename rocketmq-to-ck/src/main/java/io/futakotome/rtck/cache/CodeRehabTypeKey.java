package io.futakotome.rtck.cache;

import java.util.Objects;

public class CodeRehabTypeKey {
    private String code;
    private Integer rehabType;

    public CodeRehabTypeKey() {
    }

    public CodeRehabTypeKey(String code, Integer rehabType) {
        this.code = code;
        this.rehabType = rehabType;
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CodeRehabTypeKey that = (CodeRehabTypeKey) object;
        return Objects.equals(code, that.code) && Objects.equals(rehabType, that.rehabType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, rehabType);
    }

    @Override
    public String toString() {
        return "code=>" + code + ",rehabType=>" + rehabType;
    }
}
