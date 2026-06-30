package io.futakotome.trade.dto.message;

public class InformationLink {
    private String title;          // 资讯标题
    private String url;            // 资讯链接

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
