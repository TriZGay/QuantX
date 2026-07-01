package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.HeatMapDataContent;

public class HeatMapDataWsMessage implements Message {
    private Integer market; //Qot_Common.QotMarket,股票市场
    private Integer sortField; //SortField,排序字段,不填默认涨跌幅
    private Boolean ascend; //升序true,降序false,不填默认降序
    private Integer count; //返回条数[1,200],不填默认30
    private String page; //翻页游标,首次不传,后续传上次返回的nextPage
    private Integer plateType; //HeatMapPlateType,板块类型,不填默认行业板块
    private HeatMapDataContent content;

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

    public Boolean getAscend() {
        return ascend;
    }

    public void setAscend(Boolean ascend) {
        this.ascend = ascend;
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

    public Integer getPlateType() {
        return plateType;
    }

    public void setPlateType(Integer plateType) {
        this.plateType = plateType;
    }

    public HeatMapDataContent getContent() {
        return content;
    }

    public void setContent(HeatMapDataContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.HEAT_MAP_DATA;
    }
}
