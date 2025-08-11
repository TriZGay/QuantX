package io.futakotome.rtck.cache;

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
    public String toString() {
        return "code=>" + code + ",rehabType=>" + rehabType;
    }
}
