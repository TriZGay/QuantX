package io.futakotome.trade.dto.message;

public class CorporateActionsDividendContent {
    private String pubDate; // 公告日，格式 YYYY/MM/DD，对应市场时区
    private String statement; // 分配方案描述，如"末期息5.3港元"
    private String process; // 事件进展，如"方案实施"/"预案"；仅港股和A股的正股与信托有值
    private String recordDate; // 股权登记日，格式 YYYY/MM/DD，对应市场时区。ETF无此数据
    private String exDate; // 除权除息日，格式 YYYY/MM/DD，对应市场时区
    private String dividendPayableDate; // 派息日，格式 YYYY/MM/DD，对应市场时区
    private String fiscalYear; // 财政年度,如"2026"。仅ETF有值。

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getExDate() {
        return exDate;
    }

    public void setExDate(String exDate) {
        this.exDate = exDate;
    }

    public String getDividendPayableDate() {
        return dividendPayableDate;
    }

    public void setDividendPayableDate(String dividendPayableDate) {
        this.dividendPayableDate = dividendPayableDate;
    }

    public String getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(String fiscalYear) {
        this.fiscalYear = fiscalYear;
    }
}
