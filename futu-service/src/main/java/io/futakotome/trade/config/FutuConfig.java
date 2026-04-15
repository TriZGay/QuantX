package io.futakotome.trade.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "ft")
public class FutuConfig {
    private String url;
    private Integer port;
    private boolean isEnableEncrypt;
    private String pwd;
    private List<String> exclusionCodesWhenSnapshot;

    public List<String> getExclusionCodesWhenSnapshot() {
        return exclusionCodesWhenSnapshot;
    }

    public void setExclusionCodesWhenSnapshot(List<String> exclusionCodesWhenSnapshot) {
        this.exclusionCodesWhenSnapshot = exclusionCodesWhenSnapshot;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public boolean isEnableEncrypt() {
        return isEnableEncrypt;
    }

    public void setEnableEncrypt(boolean enableEncrypt) {
        isEnableEncrypt = enableEncrypt;
    }
}
