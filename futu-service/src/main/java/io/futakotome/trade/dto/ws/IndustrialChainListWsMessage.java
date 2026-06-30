package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.IndustrialChainListContent;

public class IndustrialChainListWsMessage implements Message {
    private Integer market;          // Qot_Common.QotMarket
    private String keyword;        // 搜索关键字(可选)
    private Integer count;           // 条数 [1,50], 默认20
    private String page;           // 翻页游标, 首次不传
    private IndustrialChainListContent content;

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

    public IndustrialChainListContent getContent() {
        return content;
    }

    public void setContent(IndustrialChainListContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.INDUSTRIAL_CHAIN_LIST;
    }
}
