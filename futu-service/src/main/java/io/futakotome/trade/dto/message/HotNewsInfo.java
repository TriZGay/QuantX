package io.futakotome.trade.dto.message;

public class HotNewsInfo {
  private Integer newsType ;        // 1=社区讨论, 2=资讯
  private String title ;          // 讨论/资讯标题
  private String newsUrl ;        // 资讯URL(newsType=2时有效)

    public Integer getNewsType() {
        return newsType;
    }

    public void setNewsType(Integer newsType) {
        this.newsType = newsType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }
}
