package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.IndustrialPlateStockContent;

import java.util.List;

public class IndustrialPlateStockWsMessage implements Message {
    private Long chainId;        //产业链ID (与plateId二选一，plateId优先)
    private Long plateId;        //产业板块ID (优先使用)
    private List<Integer> marketList;     //Qot_Common.QotMarket 市场筛选(支持HK/US/CN/JP/SG/MY，不传默认全部)
    private Integer sortField;      //SortField，排序字段，默认MarketVal(市值)
    private Boolean ascend;          //升序true/降序false，默认false(降序)
    private Integer count;          //每页数量 [1,200], 默认50
    private String page;          //翻页标记，首次不传，后续带上S2C返回的page
    private IndustrialPlateStockContent content;

    public Long getChainId() {
        return chainId;
    }

    public void setChainId(Long chainId) {
        this.chainId = chainId;
    }

    public Long getPlateId() {
        return plateId;
    }

    public void setPlateId(Long plateId) {
        this.plateId = plateId;
    }

    public List<Integer> getMarketList() {
        return marketList;
    }

    public void setMarketList(List<Integer> marketList) {
        this.marketList = marketList;
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

    public IndustrialPlateStockContent getContent() {
        return content;
    }

    public void setContent(IndustrialPlateStockContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.INDUSTRIAL_PLATE_STOCK;
    }
}
