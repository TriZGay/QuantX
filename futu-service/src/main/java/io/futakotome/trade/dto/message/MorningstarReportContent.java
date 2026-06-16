package io.futakotome.trade.dto.message;

public class MorningstarReportContent {
    private Integer ratingType;
    private Integer starRating;
    private Long starUpdateTime;
    private String starUpdateTimeStr;
    private Double fairValue;
    private MorningstarReportStringWithUpdateTime fairValueContent;
    private String economicMoatLabel;
    private MorningstarReportStringWithUpdateTime economicMoatContent;
    private String uncertaintyLabel;
    private MorningstarReportStringWithUpdateTime uncertaintyContent;
    private String financialHealthLabel;
    private MorningstarReportStringWithUpdateTime financialHealthContent;
    private String analystReportByLine;
    private Long analystReportUpdateTime;
    private String analystReportUpdateTimeStr;
    private MorningstarReportStringWithUpdateTime bullSay;
    private MorningstarReportStringWithUpdateTime bearSay;
    private String capitalAllocationLabel;
    private MorningstarReportStringWithUpdateTime capitalAllocationContent;
    private MorningstarReportStringWithUpdateTime analystNoteTitle;
    private MorningstarReportStringWithUpdateTime analystNoteContent;
    private MorningstarReportStringWithUpdateTime investmentThesisContent;
    private MorningstarReportStringWithUpdateTime fundamentalsContent;
    private MorningstarReportStringWithUpdateTime valuationContent;
    private String pdfUrl;

    public Integer getRatingType() {
        return ratingType;
    }

    public void setRatingType(Integer ratingType) {
        this.ratingType = ratingType;
    }

    public Integer getStarRating() {
        return starRating;
    }

    public void setStarRating(Integer starRating) {
        this.starRating = starRating;
    }

    public Long getStarUpdateTime() {
        return starUpdateTime;
    }

    public void setStarUpdateTime(Long starUpdateTime) {
        this.starUpdateTime = starUpdateTime;
    }

    public String getStarUpdateTimeStr() {
        return starUpdateTimeStr;
    }

    public void setStarUpdateTimeStr(String starUpdateTimeStr) {
        this.starUpdateTimeStr = starUpdateTimeStr;
    }

    public Double getFairValue() {
        return fairValue;
    }

    public void setFairValue(Double fairValue) {
        this.fairValue = fairValue;
    }

    public MorningstarReportStringWithUpdateTime getFairValueContent() {
        return fairValueContent;
    }

    public void setFairValueContent(MorningstarReportStringWithUpdateTime fairValueContent) {
        this.fairValueContent = fairValueContent;
    }

    public String getEconomicMoatLabel() {
        return economicMoatLabel;
    }

    public void setEconomicMoatLabel(String economicMoatLabel) {
        this.economicMoatLabel = economicMoatLabel;
    }

    public MorningstarReportStringWithUpdateTime getEconomicMoatContent() {
        return economicMoatContent;
    }

    public void setEconomicMoatContent(MorningstarReportStringWithUpdateTime economicMoatContent) {
        this.economicMoatContent = economicMoatContent;
    }

    public String getUncertaintyLabel() {
        return uncertaintyLabel;
    }

    public void setUncertaintyLabel(String uncertaintyLabel) {
        this.uncertaintyLabel = uncertaintyLabel;
    }

    public MorningstarReportStringWithUpdateTime getUncertaintyContent() {
        return uncertaintyContent;
    }

    public void setUncertaintyContent(MorningstarReportStringWithUpdateTime uncertaintyContent) {
        this.uncertaintyContent = uncertaintyContent;
    }

    public String getFinancialHealthLabel() {
        return financialHealthLabel;
    }

    public void setFinancialHealthLabel(String financialHealthLabel) {
        this.financialHealthLabel = financialHealthLabel;
    }

    public MorningstarReportStringWithUpdateTime getFinancialHealthContent() {
        return financialHealthContent;
    }

    public void setFinancialHealthContent(MorningstarReportStringWithUpdateTime financialHealthContent) {
        this.financialHealthContent = financialHealthContent;
    }

    public String getAnalystReportByLine() {
        return analystReportByLine;
    }

    public void setAnalystReportByLine(String analystReportByLine) {
        this.analystReportByLine = analystReportByLine;
    }

    public Long getAnalystReportUpdateTime() {
        return analystReportUpdateTime;
    }

    public void setAnalystReportUpdateTime(Long analystReportUpdateTime) {
        this.analystReportUpdateTime = analystReportUpdateTime;
    }

    public String getAnalystReportUpdateTimeStr() {
        return analystReportUpdateTimeStr;
    }

    public void setAnalystReportUpdateTimeStr(String analystReportUpdateTimeStr) {
        this.analystReportUpdateTimeStr = analystReportUpdateTimeStr;
    }

    public MorningstarReportStringWithUpdateTime getBullSay() {
        return bullSay;
    }

    public void setBullSay(MorningstarReportStringWithUpdateTime bullSay) {
        this.bullSay = bullSay;
    }

    public MorningstarReportStringWithUpdateTime getBearSay() {
        return bearSay;
    }

    public void setBearSay(MorningstarReportStringWithUpdateTime bearSay) {
        this.bearSay = bearSay;
    }

    public String getCapitalAllocationLabel() {
        return capitalAllocationLabel;
    }

    public void setCapitalAllocationLabel(String capitalAllocationLabel) {
        this.capitalAllocationLabel = capitalAllocationLabel;
    }

    public MorningstarReportStringWithUpdateTime getCapitalAllocationContent() {
        return capitalAllocationContent;
    }

    public void setCapitalAllocationContent(MorningstarReportStringWithUpdateTime capitalAllocationContent) {
        this.capitalAllocationContent = capitalAllocationContent;
    }

    public MorningstarReportStringWithUpdateTime getAnalystNoteTitle() {
        return analystNoteTitle;
    }

    public void setAnalystNoteTitle(MorningstarReportStringWithUpdateTime analystNoteTitle) {
        this.analystNoteTitle = analystNoteTitle;
    }

    public MorningstarReportStringWithUpdateTime getAnalystNoteContent() {
        return analystNoteContent;
    }

    public void setAnalystNoteContent(MorningstarReportStringWithUpdateTime analystNoteContent) {
        this.analystNoteContent = analystNoteContent;
    }

    public MorningstarReportStringWithUpdateTime getInvestmentThesisContent() {
        return investmentThesisContent;
    }

    public void setInvestmentThesisContent(MorningstarReportStringWithUpdateTime investmentThesisContent) {
        this.investmentThesisContent = investmentThesisContent;
    }

    public MorningstarReportStringWithUpdateTime getFundamentalsContent() {
        return fundamentalsContent;
    }

    public void setFundamentalsContent(MorningstarReportStringWithUpdateTime fundamentalsContent) {
        this.fundamentalsContent = fundamentalsContent;
    }

    public MorningstarReportStringWithUpdateTime getValuationContent() {
        return valuationContent;
    }

    public void setValuationContent(MorningstarReportStringWithUpdateTime valuationContent) {
        this.valuationContent = valuationContent;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }
}
