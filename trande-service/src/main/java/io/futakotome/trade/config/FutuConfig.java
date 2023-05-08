package io.futakotome.trade.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ft")
public class FutuConfig {
    private String url;
    private Integer port;
    private boolean isEnableEncrypt;

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
