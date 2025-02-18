package io.futakotome.trade.dto.message;

public class StockContent {
    private BasicInfo basic;
    //窝轮额外信息
    private WarrantExInfo warrantExData;
    //期权额外信息
    private OptionExInfo optionExData;
    //期货额外信息
    private FutureExInfo futureExData;

    public BasicInfo getBasic() {
        return basic;
    }

    public void setBasic(BasicInfo basic) {
        this.basic = basic;
    }

    public WarrantExInfo getWarrantExData() {
        return warrantExData;
    }

    public void setWarrantExData(WarrantExInfo warrantExData) {
        this.warrantExData = warrantExData;
    }

    public OptionExInfo getOptionExData() {
        return optionExData;
    }

    public void setOptionExData(OptionExInfo optionExData) {
        this.optionExData = optionExData;
    }

    public FutureExInfo getFutureExData() {
        return futureExData;
    }

    public void setFutureExData(FutureExInfo futureExData) {
        this.futureExData = futureExData;
    }

    public static class BasicInfo {
        private CommonSecurity security;
        private String id;
        private Integer lotSize;
        private Integer secType;
        private String name;
        private String listTime;
        private Boolean delisting;
        private Double listTimestamp;
        private Integer exchType;

        public CommonSecurity getSecurity() {
            return security;
        }

        public void setSecurity(CommonSecurity security) {
            this.security = security;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getLotSize() {
            return lotSize;
        }

        public void setLotSize(Integer lotSize) {
            this.lotSize = lotSize;
        }

        public Integer getSecType() {
            return secType;
        }

        public void setSecType(Integer secType) {
            this.secType = secType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getListTime() {
            return listTime;
        }

        public void setListTime(String listTime) {
            this.listTime = listTime;
        }

        public Boolean getDelisting() {
            return delisting;
        }

        public void setDelisting(Boolean delisting) {
            this.delisting = delisting;
        }

        public Double getListTimestamp() {
            return listTimestamp;
        }

        public void setListTimestamp(Double listTimestamp) {
            this.listTimestamp = listTimestamp;
        }

        public Integer getExchType() {
            return exchType;
        }

        public void setExchType(Integer exchType) {
            this.exchType = exchType;
        }
    }

    public static class WarrantExInfo {
        //窝轮类型
        private Integer type;
        private CommonSecurity owner;

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public CommonSecurity getOwner() {
            return owner;
        }

        public void setOwner(CommonSecurity owner) {
            this.owner = owner;
        }
    }

    public static class OptionExInfo {
        private Integer type;
        private CommonSecurity owner;
        //行权日
        private String strikeTime;
        //行权价
        private Double strikePrice;
        //是否停牌
        private Boolean suspend;
        //发行市场
        private String market;
        private Double strikeTimestamp;
        //指数期权类型
        private Integer indexOptionType;

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public CommonSecurity getOwner() {
            return owner;
        }

        public void setOwner(CommonSecurity owner) {
            this.owner = owner;
        }

        public String getStrikeTime() {
            return strikeTime;
        }

        public void setStrikeTime(String strikeTime) {
            this.strikeTime = strikeTime;
        }

        public Double getStrikePrice() {
            return strikePrice;
        }

        public void setStrikePrice(Double strikePrice) {
            this.strikePrice = strikePrice;
        }

        public Boolean getSuspend() {
            return suspend;
        }

        public void setSuspend(Boolean suspend) {
            this.suspend = suspend;
        }

        public String getMarket() {
            return market;
        }

        public void setMarket(String market) {
            this.market = market;
        }

        public Double getStrikeTimestamp() {
            return strikeTimestamp;
        }

        public void setStrikeTimestamp(Double strikeTimestamp) {
            this.strikeTimestamp = strikeTimestamp;
        }

        public Integer getIndexOptionType() {
            return indexOptionType;
        }

        public void setIndexOptionType(Integer indexOptionType) {
            this.indexOptionType = indexOptionType;
        }
    }

    public static class FutureExInfo {
        //最后交易日
        private String lastTradeTime;
        private Double lastTradeTimestamp;
        //是否主连
        private Boolean isMainContract;

        public String getLastTradeTime() {
            return lastTradeTime;
        }

        public void setLastTradeTime(String lastTradeTime) {
            this.lastTradeTime = lastTradeTime;
        }

        public Double getLastTradeTimestamp() {
            return lastTradeTimestamp;
        }

        public void setLastTradeTimestamp(Double lastTradeTimestamp) {
            this.lastTradeTimestamp = lastTradeTimestamp;
        }

        public Boolean getMainContract() {
            return isMainContract;
        }

        public void setMainContract(Boolean mainContract) {
            isMainContract = mainContract;
        }
    }
}
