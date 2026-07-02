package io.futakotome.trade.controller.ws;

import io.futakotome.common.message.RTEmaMessage;
import io.futakotome.common.message.RTMaMessage;
import io.futakotome.trade.dto.ws.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static io.futakotome.trade.config.WebSocketMessageBrokerConfig.BROKER_PREFIX;
import static io.futakotome.trade.controller.ws.QuantxWsController.*;

@Service
public class QuantxFutuWsService {
    private final SimpMessagingTemplate template;

    public QuantxFutuWsService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void sendNotify(String msg) {
        this.template.convertAndSend(BROKER_PREFIX + NOTIFY_URI, msg);
    }

    public void sendHistoryKQuotaDetails(HistoryKLDetailWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + HISTORY_KLINE_QUOTA_URI, message);
    }

    public void sendMarketState(MarketStateWsMessage marketStateMessage) {
        this.template.convertAndSend(BROKER_PREFIX + MARKET_STATE_URI, marketStateMessage);
    }

    public void sendCapitalDistribution(CapitalDistributionWsMessage capitalDistributionMessage) {
        this.template.convertAndSend(BROKER_PREFIX + CAPITAL_DISTR_URI, capitalDistributionMessage);
    }

    public void sendCapitalFlow(CapitalFlowWsMessage capitalFlowWsMessage) {
        this.template.convertAndSend(BROKER_PREFIX + CAPITAL_FLOW_URI, capitalFlowWsMessage);
    }

    public void sendRehabs(RehabsWsMessage rehabsWsMessage) {
        this.template.convertAndSend(BROKER_PREFIX + REHABS_URI, rehabsWsMessage);
    }

    public void sendAccounts(AccountsWsMessage accountsWsMessage) {
        this.template.convertAndSend(BROKER_PREFIX + ACCOUNTS_URI, accountsWsMessage);
    }

    public void sendAccPosition(AccPositionWsMessage accPositionWsMessage) {
        this.template.convertAndSend(BROKER_PREFIX + POSITION_URI, accPositionWsMessage);
    }

    public void sendStockFilterMessage(StockFilterWsMessage wsMessage) {
        this.template.convertAndSend(BROKER_PREFIX + STOCK_FILTER_URL, wsMessage);
    }

    public void sendAccFundsMessage(AccFundsWsMessage accFundsWsMessage) {
        this.template.convertAndSend(BROKER_PREFIX + ACC_FUNDS_URI, accFundsWsMessage);
    }

    public void sendHistoryOrders(HistoryOrdersWsMessage historyOrdersWsMessage) {
        this.template.convertAndSend(BROKER_PREFIX + HISTORY_ORDER_URI, historyOrdersWsMessage);
    }

    public void sendIncompleteOrders(IncompleteOrdersWsMessage incompleteOrdersWsMessage) {
        this.template.convertAndSend(BROKER_PREFIX + INCOMPLETE_ORDER_URI, incompleteOrdersWsMessage);
    }

    public void sendUserGroup(UserGroupWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + USER_GROUP_URI, message);
    }

    public void sendUserSecurity(UserSecurityWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + USER_SECURITY_URI, message);
    }

    public void sendGetPriceReminderListMessage(GetPriceReminderWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + GET_PRICE_REMINDER_URI, message);
    }

    public void sendIpoMessage(GetIpoWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + GET_IPO_URI, message);
    }

    public void sendStocksInPlateMessage(StockInPlateWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + STOCKS_IN_PLATE_URI, message);
    }

    public void sendFinancialRevenueBreakDown(FinancialReWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + FINANCIAL_REVENUE_BREAKDOWN, message);
    }

    public void sendFinancialEarningMove(FinancialEarningMoveWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + FINANCIAL_EARNING_MOVE, message);
    }

    public void sendFinancialEarningPriceHistory(FinancialEarningPriceHistoryWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + FINANCIAL_EARNING_PRICE_HISTORY, message);
    }

    public void sendAnalystConsensus(AnalystConsensusWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + ANALYST_CONSENSUS, message);
    }

    public void sendMorningstarReport(MorningstarReportWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + MORNINGSTAR_REPORT, message);
    }

    public void sendResearchRatingSummary(ResearchRatingSummaryWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + RATING_SUMMARY, message);
    }

    public void sendFinancialStatements(FinancialStatementWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + FINANCIAL_STATEMENTS, message);
    }

    public void sendValuationDetail(ValuationDetailWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + VALUATION_DETAIL, message);
    }

    public void sendValuationPlateStockList(ValuationPlateStockListWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + VALUATION_P_S_LIST, message);
    }

    public void sendCorporateActionsDividend(CorporateActionsDividendsWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + CO_ACTIONS_DIVIDEND, message);
    }

    public void sendCorporateActionsBuyback(CorporateActionsBuybackWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + CO_ACTIONS_BUYBACK, message);
    }

    public void sendCorporateActionsStockSplits(CorporateActionsStockSplitsWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + CO_ACTIONS_STOCK_SPLITS, message);
    }

    public void sendShareholderOvr(ShareholderOvrWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + SHAREHOLDER_OVR, message);
    }

    public void sendShareholderHoldingChange(ShareholderHoldingChangeWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + SHAREHOLDER_HOLDING_CHANGE, message);
    }

    public void sendShareholderHolderDetail(ShareholderHolderDetailWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + SHAREHOLDER_HOLDER_DETAIL, message);
    }

    public void sendShareholderInstitutional(ShareholderInstitutionalWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + SHAREHOLDER_INSTITUTIONAL, message);
    }

    public void sendInsiderHolderList(InsiderHolderListWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + INSIDER_HOLDER_LIST, message);
    }

    public void sendInsiderTradeList(InsiderTradeListWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + INSIDER_TRADE_LIST, message);
    }

    public void sendCompanyProfile(CompanyProfileWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + COMPANY_PROFILE, message);
    }

    public void sendCompanyExecutives(CompanyExecutivesWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + COMPANY_EXECUTIVES, message);
    }

    public void sendCompanyExecutiveBackground(CompanyExecutiveBackgroungWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + COMPANY_EXECUTIVE_BACKGROUND, message);
    }

    public void sendCompanyOpEfficiency(CompanyOpEfficiencyWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + COMPANY_OP_EFFICIENCY, message);
    }

    public void sendTopTenBrokersBuySell(TopTenBrokersWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + TOP_TEN_BROKERS_BUY_SELL, message);
    }


    public void sendDailyShotVolume(DailyShortVolumeWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + DAILY_SHORT_VOLUME, message);
    }

    public void sendShortInterest(ShortInterestWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + SHORT_INTEREST, message);
    }

    public void sendInstitutionList(InstitutionListWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + INSTITUTION_LIST, message);
    }

    public void sendInstitutionProfile(InstitutionProfileWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + INSTITUTION_PROFILE, message);
    }

    public void sendInstitutionDistribution(InstitutionDistributionWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + INSTITUTION_DISTRIBUTION, message);
    }

    public void sendInstitutionHoldingChange(InstitutionHoldingChangeWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + INSTITUTION_HOLDING_CHANGE, message);
    }

    public void sendInstitutionHoldingList(InstitutionHoldingListWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + INSTITUTION_HOLDING_LIST, message);
    }

    public void sendArkFundHolding(ArkFundHoldingWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + ARK_FUND_HOLDING, message);
    }

    public void sendArkStockDynamic(ArkStockDynamicWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + ARK_STOCK_DYNAMIC, message);
    }

    public void sendArkActiveTransaction(ArkActiveTransactionWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + ARK_ACTIVE_TRANSACTION, message);
    }

    public void sendIndustrialChainList(IndustrialChainListWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + INDUSTRIAL_CHAIN_LIST, message);
    }

    public void sendIndustrialChainDetail(IndustrialChainDetailWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + INDUSTRIAL_CHAIN_DETAIL, message);
    }

    public void sendIndustrialChainByPlate(IndustrialChainByPlateWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + INDUSTRIAL_CHAIN_BY_PLATE, message);
    }

    public void sendIndustrialPlateInfo(IndustrialPlateInfoWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + INDUSTRIAL_PLATE_INFO, message);
    }

    public void sendIndustrialPlateStock(IndustrialPlateStockWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + INDUSTRIAL_PLATE_STOCK, message);
    }

    public void sendHeatMapData(HeatMapDataWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + HEAT_MAP_DATA, message);
    }

    public void sendRiseFallDistribution(RiseFallDistributionWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + RISE_FALL_DISTRIBUTION, message);
    }

    public void sendShortSellRank(ShortSellRankWsMessage message) {
        this.template.convertAndSend(BROKER_PREFIX + SHORT_SELL_RANK, message);
    }

    //ma
    public void sendRtMa5(RTMaMessage maMessage) {
        this.template.convertAndSend(BROKER_PREFIX + MA5_URI, maMessage);
    }

    public void sendRtMa10(RTMaMessage maMessage) {
        this.template.convertAndSend(BROKER_PREFIX + MA10_URI, maMessage);
    }

    public void sendRtMa20(RTMaMessage maMessage) {
        this.template.convertAndSend(BROKER_PREFIX + MA20_URI, maMessage);
    }

    public void sendRtMa30(RTMaMessage maMessage) {
        this.template.convertAndSend(BROKER_PREFIX + MA30_URI, maMessage);
    }

    public void sendRtMa60(RTMaMessage maMessage) {
        this.template.convertAndSend(BROKER_PREFIX + MA60_URI, maMessage);
    }

    public void sendRtMa120(RTMaMessage maMessage) {
        this.template.convertAndSend(BROKER_PREFIX + MA120_URI, maMessage);
    }

    //ema
    public void sendRtEma5(RTEmaMessage emaMessage) {
        this.template.convertAndSend(BROKER_PREFIX + EMA5_URI, emaMessage);
    }

}
