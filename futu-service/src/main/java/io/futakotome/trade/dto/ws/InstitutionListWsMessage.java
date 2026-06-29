package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.InstitutionListContent;

public class InstitutionListWsMessage implements Message {
    private Integer market;
    private Integer sortField;
    private Integer sortDir;
    private Integer count;
    private String page;
    private String namePart;
    private InstitutionListContent content;

    public InstitutionListContent getContent() {
        return content;
    }

    public void setContent(InstitutionListContent content) {
        this.content = content;
    }

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public Integer getSortField() {
        return sortField;
    }

    public void setSortField(Integer sortField) {
        this.sortField = sortField;
    }

    public Integer getSortDir() {
        return sortDir;
    }

    public void setSortDir(Integer sortDir) {
        this.sortDir = sortDir;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getNamePart() {
        return namePart;
    }

    public void setNamePart(String namePart) {
        this.namePart = namePart;
    }

    @Override
    public MessageType getType() {
        return MessageType.INSTITUTION_LIST;
    }
}
