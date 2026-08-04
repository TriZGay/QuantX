package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.IndicatorListContent;

public class IndicatorListWsMessage implements Message {
    private String searchKey;
    private Integer langType;
    private Integer searchMode;
    private IndicatorListContent content;

    public IndicatorListContent getContent() {
        return content;
    }

    public void setContent(IndicatorListContent content) {
        this.content = content;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public Integer getLangType() {
        return langType;
    }

    public void setLangType(Integer langType) {
        this.langType = langType;
    }

    public Integer getSearchMode() {
        return searchMode;
    }

    public void setSearchMode(Integer searchMode) {
        this.searchMode = searchMode;
    }

    @Override
    public MessageType getType() {
        return MessageType.INDICATOR_LIST;
    }
}
