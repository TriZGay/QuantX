package io.futakotome.trade.dto.message;

public class StockSplitItem {
    // 通用字段
    private Long dirDeciPubDate;  // 公告日时间戳（秒）
    private String dirDeciPubDateStr;  // 公告日字符串，格式 YYYY-MM-DD，对应市场时区
    private String reformType;  // 重组方式
    private String rate;  // 比率

    // 港股专有字段（仅港股的正股与信托有值）
    private Long exDate;  // 除权日时间戳（秒）
    private String exDateStr;  // 除权日字符串，格式 YYYY-MM-DD，对应市场时区
    private Long smDeciDate;  // 决议日时间戳（秒）
    private String smDeciDateStr;  // 决议日字符串，格式 YYYY-MM-DD，对应市场时区
    private Long tempTradeBeginDate;  // 临时买卖日时间戳（秒）
    private String tempTradeBeginDateStr; // 临时买卖日字符串，格式 YYYY-MM-DD，对应市场时区
    private Long simulTradeBeginDate; // 并行买卖开始日时间戳（秒）
    private String simulTradeBeginDateStr; // 并行买卖开始日字符串，格式 YYYY-MM-DD，对应市场时区
    private Long simulTradeEndDate; // 并行买卖结束日时间戳（秒）
    private String simulTradeEndDateStr; // 并行买卖结束日字符串，格式 YYYY-MM-DD，对应市场时区
    private String eventStatus; // 事件进程（如：方案实施）
    private Double newParValue; // 新面值
    private String tempShareCode; // 临时证券代码（如：02988）
    private String tempShareAbbrName; // 临时证券简称（如：腾讯控股）
    private Long newTradeUnit; // 新买卖单位（如：100）
    private Double sharesAfterEffect; // 生效后股数（股）

    public Long getDirDeciPubDate() {
        return dirDeciPubDate;
    }

    public void setDirDeciPubDate(Long dirDeciPubDate) {
        this.dirDeciPubDate = dirDeciPubDate;
    }

    public String getDirDeciPubDateStr() {
        return dirDeciPubDateStr;
    }

    public void setDirDeciPubDateStr(String dirDeciPubDateStr) {
        this.dirDeciPubDateStr = dirDeciPubDateStr;
    }

    public String getReformType() {
        return reformType;
    }

    public void setReformType(String reformType) {
        this.reformType = reformType;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Long getExDate() {
        return exDate;
    }

    public void setExDate(Long exDate) {
        this.exDate = exDate;
    }

    public String getExDateStr() {
        return exDateStr;
    }

    public void setExDateStr(String exDateStr) {
        this.exDateStr = exDateStr;
    }

    public Long getSmDeciDate() {
        return smDeciDate;
    }

    public void setSmDeciDate(Long smDeciDate) {
        this.smDeciDate = smDeciDate;
    }

    public String getSmDeciDateStr() {
        return smDeciDateStr;
    }

    public void setSmDeciDateStr(String smDeciDateStr) {
        this.smDeciDateStr = smDeciDateStr;
    }

    public Long getTempTradeBeginDate() {
        return tempTradeBeginDate;
    }

    public void setTempTradeBeginDate(Long tempTradeBeginDate) {
        this.tempTradeBeginDate = tempTradeBeginDate;
    }

    public String getTempTradeBeginDateStr() {
        return tempTradeBeginDateStr;
    }

    public void setTempTradeBeginDateStr(String tempTradeBeginDateStr) {
        this.tempTradeBeginDateStr = tempTradeBeginDateStr;
    }

    public Long getSimulTradeBeginDate() {
        return simulTradeBeginDate;
    }

    public void setSimulTradeBeginDate(Long simulTradeBeginDate) {
        this.simulTradeBeginDate = simulTradeBeginDate;
    }

    public String getSimulTradeBeginDateStr() {
        return simulTradeBeginDateStr;
    }

    public void setSimulTradeBeginDateStr(String simulTradeBeginDateStr) {
        this.simulTradeBeginDateStr = simulTradeBeginDateStr;
    }

    public Long getSimulTradeEndDate() {
        return simulTradeEndDate;
    }

    public void setSimulTradeEndDate(Long simulTradeEndDate) {
        this.simulTradeEndDate = simulTradeEndDate;
    }

    public String getSimulTradeEndDateStr() {
        return simulTradeEndDateStr;
    }

    public void setSimulTradeEndDateStr(String simulTradeEndDateStr) {
        this.simulTradeEndDateStr = simulTradeEndDateStr;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public Double getNewParValue() {
        return newParValue;
    }

    public void setNewParValue(Double newParValue) {
        this.newParValue = newParValue;
    }

    public String getTempShareCode() {
        return tempShareCode;
    }

    public void setTempShareCode(String tempShareCode) {
        this.tempShareCode = tempShareCode;
    }

    public String getTempShareAbbrName() {
        return tempShareAbbrName;
    }

    public void setTempShareAbbrName(String tempShareAbbrName) {
        this.tempShareAbbrName = tempShareAbbrName;
    }

    public Long getNewTradeUnit() {
        return newTradeUnit;
    }

    public void setNewTradeUnit(Long newTradeUnit) {
        this.newTradeUnit = newTradeUnit;
    }

    public Double getSharesAfterEffect() {
        return sharesAfterEffect;
    }

    public void setSharesAfterEffect(Double sharesAfterEffect) {
        this.sharesAfterEffect = sharesAfterEffect;
    }
}
