package io.futakotome.trade.dto.ws;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.futakotome.trade.dto.message.ModifyOrderWsMessage;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ConnectWsMessage.class, name = "CONNECT"),
        @JsonSubTypes.Type(value = MarketStateWsMessage.class, name = "MARKET_STATE"),
        @JsonSubTypes.Type(value = HistoryKLDetailWsMessage.class, name = "KL_HISTORY_DETAIL"),
        @JsonSubTypes.Type(value = SubOrUnSubWsMessage.class, name = "SUBSCRIPTION"),
        @JsonSubTypes.Type(value = HistoryKLWsMessage.class, name = "KL_HISTORY"),
        @JsonSubTypes.Type(value = PlatesWsMessage.class, name = "PLATES"),
        @JsonSubTypes.Type(value = StockInPlateWsMessage.class, name = "STOCK_IN_PLATE"),
        @JsonSubTypes.Type(value = StocksWsMessage.class, name = "STOCKS"),
        @JsonSubTypes.Type(value = StockOwnerPlateWsMessage.class, name = "STOCK_OWNER_PLATE"),
        @JsonSubTypes.Type(value = CapitalFlowWsMessage.class, name = "CAPITAL_FLOW"),
        @JsonSubTypes.Type(value = CapitalDistributionWsMessage.class, name = "CAPITAL_DISTRIBUTION"),
        @JsonSubTypes.Type(value = RehabsWsMessage.class, name = "REHABS"),
        @JsonSubTypes.Type(value = SnapshotWsMessage.class, name = "SNAPSHOT"),
        @JsonSubTypes.Type(value = AccountsWsMessage.class, name = "ACCOUNTS"),
        @JsonSubTypes.Type(value = AccSubscribeWsMessage.class, name = "ACC_SUBSCRIBE"),
        @JsonSubTypes.Type(value = AccPositionWsMessage.class, name = "ACC_POSITION"),
        @JsonSubTypes.Type(value = StockFilterWsMessage.class, name = "STOCK_FILTER"),
        @JsonSubTypes.Type(value = AccFundsWsMessage.class, name = "ACC_FUNDS"),
        @JsonSubTypes.Type(value = PlaceOrderWsMessage.class, name = "PLACE_ORDER"),
        @JsonSubTypes.Type(value = ModifyOrderWsMessage.class, name = "MODIFY_ORDER"),
        @JsonSubTypes.Type(value = HistoryOrdersWsMessage.class, name = "HISTORY_ORDER"),
        @JsonSubTypes.Type(value = IncompleteOrdersWsMessage.class, name = "INCOMPLETE_ORDER"),
        @JsonSubTypes.Type(value = UserGroupWsMessage.class, name = "USER_GROUP"),
        @JsonSubTypes.Type(value = UserSecurityWsMessage.class, name = "USER_SECURITY"),
        @JsonSubTypes.Type(value = SetPriceReminderWsMessage.class, name = "SET_PRICE_REMINDER"),
        @JsonSubTypes.Type(value = GetPriceReminderWsMessage.class, name = "GET_PRICE_REMINDER"),
        @JsonSubTypes.Type(value = GetIpoWsMessage.class, name = "IPO"),
        @JsonSubTypes.Type(value = StockInPlateByMarketWsMessage.class, name = "STOCK_IN_PLATE_BY_MARKET"),
        @JsonSubTypes.Type(value = FinancialEarningMoveWsMessage.class, name = "FINANCIAL_EARNING_MOVE"),
        @JsonSubTypes.Type(value = FinancialReWsMessage.class, name = "FINANCIAL_REVENUE_BREAKDOWN"),
        @JsonSubTypes.Type(value = FinancialEarningPriceHistoryWsMessage.class, name = "FINANCIAL_EARNING_PRICE_HISTORY"),
        @JsonSubTypes.Type(value = AnalystConsensusWsMessage.class, name = "ANALYST_CONSENSUS"),
        @JsonSubTypes.Type(value = MorningstarReportWsMessage.class, name = "RESEARCH_MORNINGSTAR_REPORT"),
        @JsonSubTypes.Type(value = ResearchRatingSummaryWsMessage.class, name = "RESEARCH_RATING_SUMMARY"),
        @JsonSubTypes.Type(value = FinancialStatementWsMessage.class, name = "FINANCIAL_STATEMENTS"),
        @JsonSubTypes.Type(value = ValuationDetailWsMessage.class, name = "VALUATION_DETAIL"),
        @JsonSubTypes.Type(value = ValuationPlateStockListWsMessage.class, name = "VALUATION_P_S_LIST"),
        @JsonSubTypes.Type(value = CorporateActionsDividendsWsMessage.class, name = "CO_ACTIONS_DIVIDEND"),
        @JsonSubTypes.Type(value = CorporateActionsBuybackWsMessage.class, name = "CO_ACTIONS_BUYBACK"),
        @JsonSubTypes.Type(value = CorporateActionsStockSplitsWsMessage.class, name = "CO_ACTIONS_STOCK_SPLITS"),
        @JsonSubTypes.Type(value = ShareholderOvrWsMessage.class, name = "SHAREHOLDER_OVR"),

        @JsonSubTypes.Type(value = RealTimeBaseQuoteMessage.class, name = "RT_BASIC_QUOTE"),
        @JsonSubTypes.Type(value = RealTimeKLMessage.class, name = "RT_KL"),
        @JsonSubTypes.Type(value = RealTimeTickerMessage.class, name = "RT_TICKER"),
        @JsonSubTypes.Type(value = TimeShareMessage.class, name = "RT_TIME_SHARE"),
        @JsonSubTypes.Type(value = BrokerMessage.class, name = "RT_BROKERS")
})
public interface Message {
    MessageType getType();
}
