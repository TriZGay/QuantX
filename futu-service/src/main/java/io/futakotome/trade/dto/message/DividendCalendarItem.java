package io.futakotome.trade.dto.message;

public class DividendCalendarItem {
    private CommonSecurity security;      // 股票
    private String name;                        // 股票名称
    private String statement;                   // 方案说明
    private String recordDate;                  // 股权登记日 ("YYYY-MM-DD")
    private String exDate;                      // 除净日 ("YYYY-MM-DD")
    private String dividendPayableDate;         // 派息日 ("YYYY-MM-DD")

    public CommonSecurity getSecurity() {
        return security;
    }

    public void setSecurity(CommonSecurity security) {
        this.security = security;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
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
}
