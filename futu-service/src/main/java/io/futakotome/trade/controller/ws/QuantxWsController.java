package io.futakotome.trade.controller.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.futakotome.trade.dto.message.CommonSecurity;
import io.futakotome.trade.dto.message.ModifyOrderWsMessage;
import io.futakotome.trade.dto.ws.*;
import io.futakotome.trade.service.FTQotService;
import io.futakotome.trade.service.FTTradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import static io.futakotome.trade.config.WebSocketMessageBrokerConfig.ENDPOINT_NOTIFY;

@Controller
public class QuantxWsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuantxWsController.class);
    public static final String NOTIFY_URI = "/notify";
    public static final String HISTORY_KLINE_QUOTA_URI = "/history_k_quo";
    public static final String MARKET_STATE_URI = "/market_state";
    public static final String CAPITAL_DISTR_URI = "/capital_distr";
    public static final String CAPITAL_FLOW_URI = "/capital_flow";
    public static final String REHABS_URI = "/rehabs";
    public static final String ACCOUNTS_URI = "/accounts";
    public static final String POSITION_URI = "/positions";
    public static final String STOCK_FILTER_URL = "/stock_filter";
    public static final String ACC_FUNDS_URI = "/acc_funds";
    public static final String HISTORY_ORDER_URI = "/history_orders";
    public static final String INCOMPLETE_ORDER_URI = "/incomplete_orders";
    public static final String USER_GROUP_URI = "/user_group";
    public static final String USER_SECURITY_URI = "/user_security";
    public static final String GET_PRICE_REMINDER_URI = "/get_price_reminders";
    public static final String GET_IPO_URI = "/ipo";
    public static final String STOCKS_IN_PLATE_URI = "/stocks_in_plate";
    public static final String FINANCIAL_EARNING_MOVE = "/fin_earning_mov";
    public static final String FINANCIAL_REVENUE_BREAKDOWN = "/fin_reve_bd";
    public static final String FINANCIAL_EARNING_PRICE_HISTORY = "/fin_earning_ph";
    public static final String FINANCIAL_STATEMENTS = "/fin_state";
    public static final String ANALYST_CONSENSUS = "/ana_con";
    public static final String MORNINGSTAR_REPORT = "/morningstar_report";
    public static final String RATING_SUMMARY = "/rating_summary";
    public static final String VALUATION_DETAIL = "/valuation_detail";
    public static final String VALUATION_P_S_LIST = "/valuation_p_s_list";
    public static final String CO_ACTIONS_DIVIDEND = "/co_act_dividend";
    public static final String CO_ACTIONS_BUYBACK = "/co_act_buyback";
    public static final String CO_ACTIONS_STOCK_SPLITS = "/co_act_ss";
    public static final String SHAREHOLDER_OVR = "/shr_ovr";
    public static final String SHAREHOLDER_HOLDING_CHANGE = "/shr_hc";
    public static final String SHAREHOLDER_HOLDER_DETAIL = "/shr_hd";
    public static final String SHAREHOLDER_INSTITUTIONAL = "/shr_ist";
    public static final String INSIDER_HOLDER_LIST = "/insider_holder_list";
    public static final String INSIDER_TRADE_LIST = "/insider_trade_list";
    public static final String COMPANY_PROFILE = "/company_profile";
    public static final String COMPANY_EXECUTIVES = "/company_executives";
    public static final String COMPANY_EXECUTIVE_BACKGROUND = "/company_executive_background";
    public static final String COMPANY_OP_EFFICIENCY = "/company_op_efficiency";
    public static final String TOP_TEN_BROKERS_BUY_SELL = "/top_ten_brokers_buy_sell";
    public static final String DAILY_SHORT_VOLUME = "/daily_short_volume";
    public static final String SHORT_INTEREST = "/short_interest";
    public static final String INSTITUTION_LIST = "/institution_list";
    public static final String INSTITUTION_PROFILE = "/institution_profile";
    public static final String INSTITUTION_DISTRIBUTION = "/institution_distr";
    public static final String INSTITUTION_HOLDING_CHANGE = "/institution_holding_change";
    public static final String INSTITUTION_HOLDING_LIST = "/institution_holding_list";
    public static final String ARK_FUND_HOLDING = "/ark_fund_holding";
    public static final String ARK_STOCK_DYNAMIC = "/ark_stock_fund";
    public static final String ARK_ACTIVE_TRANSACTION = "/ark_active_transaction";
    public static final String INDUSTRIAL_CHAIN_LIST = "/industrial_chain_list";
    public static final String INDUSTRIAL_CHAIN_DETAIL = "/industrial_chain_detail";
    public static final String INDUSTRIAL_CHAIN_BY_PLATE = "/industrial_chain_by_plate";
    public static final String INDUSTRIAL_PLATE_INFO = "/industrial_plate_info";
    public static final String INDUSTRIAL_PLATE_STOCK = "/industrial_plate_stock";
    public static final String HEAT_MAP_DATA = "/heatmap";
    public static final String RISE_FALL_DISTRIBUTION = "/rise_fall_distribution";
    public static final String SHORT_SELL_RANK = "/short_sell_rank";
    public static final String HIGH_DIVIDEND_SOE_RANK = "/high_dividend_soe_rank";
    public static final String HOT_LIST = "/hot_list";
    public static final String TOP_MOVERS_RANK = "/top_movers_rank";
    public static final String MARCO_INDIES = "/marco_indies";
    public static final String MARCO_INDIES_HISTORY = "/marco_indies_history";
    public static final String DIVIDEND_RANK = "/dividend_rank";
    public static final String EARNINGS_CALENDAR = "/earnings_calendar";
    public static final String DIVIDEND_CALENDAR = "/dividend_calendar";
    public static final String ECONOMIC_CALENDAR = "/economic_calendar";
    public static final String EARNINGS_BEAT_RANK = "/earnings_beat_rank";
    //ma
    public static final String MA5_URI = "/ma5";
    public static final String MA10_URI = "/ma10";
    public static final String MA20_URI = "/ma20";
    public static final String MA30_URI = "/ma30";
    public static final String MA60_URI = "/ma60";
    public static final String MA120_URI = "/ma120";
    //ema
    public static final String EMA5_URI = "/ema5";

    private final FTQotService ftQotService;
    private final FTTradeService ftTradeService;
    private final ObjectMapper objectMapper;

    public QuantxWsController(FTQotService ftQotService, FTTradeService ftTradeService, ObjectMapper objectMapper) {
        this.ftQotService = ftQotService;
        this.ftTradeService = ftTradeService;
        this.objectMapper = objectMapper;
    }

    //这里的`/notify`是stomp client给server发送数据的destination,是有/quantx/ft前缀拼接的
    @MessageMapping(ENDPOINT_NOTIFY)
    public void notifyMessage(String message) {
        try {
            Message messageClz = objectMapper.readValue(message, Message.class);
            if (messageClz.getType().equals(MessageType.MARKET_STATE)) {
                //请求市场状态
                ftQotService.sendGlobalMarketStateRequest();
            } else if (messageClz.getType().equals(MessageType.CONNECT)) {
                //连接或断开连接
                ConnectWsMessage connectWsMessage = (ConnectWsMessage) messageClz;
                if (connectWsMessage.isConnected()) {
                    ftQotService.connect();
                    ftTradeService.connect();
                } else {
                    ftQotService.disconnect();
                    ftTradeService.disconnect();
                }
            } else if (messageClz.getType().equals(MessageType.KL_HISTORY_DETAIL)) {
                //历史K额度查询
                ftQotService.sendHistoryKLineDetailRequest();
            } else if (messageClz.getType().equals(MessageType.REFRESH_SUB)) {
                //刷新订阅信息
                ftQotService.sendSubInfoRequest();
            } else if (messageClz.getType().equals(MessageType.SUBSCRIPTION)) {
                //订阅或取消订阅
                SubOrUnSubWsMessage subMessage = (SubOrUnSubWsMessage) messageClz;
                if (subMessage.getUnsub()) {
                    ftQotService.cancelSubscribe(subMessage);
                } else {
                    ftQotService.subscribeRequest(subMessage);
                }
            } else if (messageClz.getType().equals(MessageType.TRADE_DATE)) {
                //请求交易日期
                ftQotService.syncTradeDate();
            } else if (messageClz.getType().equals(MessageType.KL_HISTORY)) {
                //请求历史K线数据
                HistoryKLWsMessage historyKLWsMessage = (HistoryKLWsMessage) messageClz;
                ftQotService.sendHistoryKLineRequest(historyKLWsMessage);
            } else if (messageClz.getType().equals(MessageType.PLATES)) {
                //同步板块数据
                PlatesWsMessage platesWsMessage = (PlatesWsMessage) messageClz;
                ftQotService.syncPlateInfo(platesWsMessage.getMarkets());
            } else if (messageClz.getType().equals(MessageType.STOCK_IN_PLATE)) {
                //同步板块下股票数据
                StockInPlateWsMessage stockInPlateWsMessage = (StockInPlateWsMessage) messageClz;
                ftQotService.syncStockInPlate(stockInPlateWsMessage.getPlate());
            } else if (messageClz.getType().equals(MessageType.STOCKS)) {
                //同步 静态标的物
                StocksWsMessage stocksWsMessage = (StocksWsMessage) messageClz;
                ftQotService.syncStaticInfo(stocksWsMessage.getMarket(), stocksWsMessage.getStockType());
            } else if (messageClz.getType().equals(MessageType.STOCK_OWNER_PLATE)) {
                //同步 股票所属板块
                StockOwnerPlateWsMessage stockOwnerPlateWsMessage = (StockOwnerPlateWsMessage) messageClz;
                ftQotService.syncStockOwnerPlateInfo(stockOwnerPlateWsMessage.getSecurities());
            } else if (messageClz.getType().equals(MessageType.CAPITAL_DISTRIBUTION)) {
                //查询资金分布
                CapitalDistributionWsMessage capitalDistributionWsMessage = (CapitalDistributionWsMessage) messageClz;
                ftQotService.syncCapitalDistribution(capitalDistributionWsMessage.getSecurity().getMarket(), capitalDistributionWsMessage.getSecurity().getCode());
            } else if (messageClz.getType().equals(MessageType.CAPITAL_FLOW)) {
                //查询资金流向
                CapitalFlowWsMessage capitalFlowWsMessage = (CapitalFlowWsMessage) messageClz;
                CommonSecurity security = capitalFlowWsMessage.getSecurity();
                ftQotService.syncCapitalFlow(security.getMarket(), security.getCode(), capitalFlowWsMessage.getPeriodType(), capitalFlowWsMessage.getBeginTime(), capitalFlowWsMessage.getEndTime());
            } else if (messageClz.getType().equals(MessageType.REHABS)) {
                //查询复权因子
                RehabsWsMessage rehabsWsMessage = (RehabsWsMessage) messageClz;
                ftQotService.sendRehabRequest(rehabsWsMessage.getSecurity().getMarket(), rehabsWsMessage.getSecurity().getCode());
            } else if (messageClz.getType().equals(MessageType.SNAPSHOT)) {
                //查询快照数据
                SnapshotWsMessage request = (SnapshotWsMessage) messageClz;
                ftQotService.syncSnapshotData(request);
            } else if (messageClz.getType().equals(MessageType.ACCOUNTS)) {
                //查询交易账号
                ftTradeService.requestAccounts();
            } else if (messageClz.getType().equals(MessageType.ACC_SUBSCRIBE)) {
                //订阅账号
                AccSubscribeWsMessage accSubscribeWsMessage = (AccSubscribeWsMessage) messageClz;
                ftTradeService.accSubscribe(accSubscribeWsMessage);
            } else if (messageClz.getType().equals(MessageType.ACC_POSITION)) {
                //查询账号持仓
                AccPositionWsMessage accPositionWsMessage = (AccPositionWsMessage) messageClz;
                ftTradeService.requestAccPosition(accPositionWsMessage);
            } else if (messageClz.getType().equals(MessageType.STOCK_FILTER)) {
                //选股
                StockFilterWsMessage stockFilterWsMessage = (StockFilterWsMessage) messageClz;
                ftQotService.sendStockFilterRequest(stockFilterWsMessage);
            } else if (messageClz.getType().equals(MessageType.ACC_FUNDS)) {
                //账户资金
                AccFundsWsMessage accFundsWsMessage = (AccFundsWsMessage) messageClz;
                ftTradeService.requestAccFunds(accFundsWsMessage);
            } else if (messageClz.getType().equals(MessageType.PLACE_ORDER)) {
                //下单
                PlaceOrderWsMessage placeOrderWsMessage = (PlaceOrderWsMessage) messageClz;
                ftTradeService.requestPlaceOrder(placeOrderWsMessage);
            } else if (messageClz.getType().equals(MessageType.MODIFY_ORDER)) {
                //改撤单
                ModifyOrderWsMessage modifyOrderWsMessage = (ModifyOrderWsMessage) messageClz;
                ftTradeService.requestModifyOrder(modifyOrderWsMessage);
            } else if (messageClz.getType().equals(MessageType.HISTORY_ORDER)) {
                //查询历史订单
                HistoryOrdersWsMessage historyOrdersWsMessage = (HistoryOrdersWsMessage) messageClz;
                ftTradeService.requestHistoryOrder(historyOrdersWsMessage);
            } else if (messageClz.getType().equals(MessageType.INCOMPLETE_ORDER)) {
                //查询未完成订单
                IncompleteOrdersWsMessage incompleteOrdersWsMessage = (IncompleteOrdersWsMessage) messageClz;
                ftTradeService.requestInCompleteOrder(incompleteOrdersWsMessage);
            } else if (messageClz.getType().equals(MessageType.USER_GROUP)) {
                //查询自选股分组
                ftQotService.sendUserGroupRequest();
            } else if (messageClz.getType().equals(MessageType.USER_SECURITY)) {
                //查询自选股列表
                UserSecurityWsMessage request = (UserSecurityWsMessage) messageClz;
                ftQotService.sendUserSecurityRequest(request);
            } else if (messageClz.getType().equals(MessageType.SET_PRICE_REMINDER)) {
                //设置到价提醒
                SetPriceReminderWsMessage request = (SetPriceReminderWsMessage) messageClz;
                ftQotService.sendSetReminderRequest(request);
            } else if (messageClz.getType().equals(MessageType.GET_PRICE_REMINDER)) {
                //获取到价提醒列表
                GetPriceReminderWsMessage request = (GetPriceReminderWsMessage) messageClz;
                ftQotService.sendGetReminderRequest(request);
            } else if (messageClz.getType().equals(MessageType.IPO)) {
                //ipo
                GetIpoWsMessage request = (GetIpoWsMessage) messageClz;
                ftQotService.sendGetIpoRequest(request);
            } else if (messageClz.getType().equals(MessageType.STOCK_IN_PLATE_BY_MARKET)) {
                //按市场建立板块和股票的关系
                StockInPlateByMarketWsMessage request = (StockInPlateByMarketWsMessage) messageClz;
                ftQotService.syncStockInPlateByMarket(request.getMarket());
            } else if (messageClz.getType().equals(MessageType.FINANCIAL_REVENUE_BREAKDOWN)) {
                //主营构成
                FinancialReWsMessage req = (FinancialReWsMessage) messageClz;
                ftQotService.syncFinancialRevenueBreakDown(req);
            } else if (messageClz.getType().equals(MessageType.ANALYST_CONSENSUS)) {
                //分析师评级概述
                AnalystConsensusWsMessage req = (AnalystConsensusWsMessage) messageClz;
                ftQotService.syncAnalystConsensus(req);
            } else if (messageClz.getType().equals(MessageType.FINANCIAL_EARNING_MOVE)) {
                //财报日前后价格涨跌幅表现
                FinancialEarningMoveWsMessage req = (FinancialEarningMoveWsMessage) messageClz;
                ftQotService.syncFinancialEarningMove(req);
            } else if (messageClz.getType().equals(MessageType.FINANCIAL_EARNING_PRICE_HISTORY)) {
                //获取财报日前后股价历史
                FinancialEarningPriceHistoryWsMessage req = (FinancialEarningPriceHistoryWsMessage) messageClz;
                ftQotService.syncFinancialEarningPriceHistory(req);
            } else if (messageClz.getType().equals(MessageType.RESEARCH_MORNINGSTAR_REPORT)) {
                //获取晨星研究报告
                MorningstarReportWsMessage req = (MorningstarReportWsMessage) messageClz;
                ftQotService.syncMorningStarReport(req);
            } else if (messageClz.getType().equals(MessageType.RESEARCH_RATING_SUMMARY)) {
                //获取评级汇总
                ResearchRatingSummaryWsMessage req = (ResearchRatingSummaryWsMessage) messageClz;
                ftQotService.syncResearchRatingSummary(req);
            } else if (messageClz.getType().equals(MessageType.FINANCIAL_STATEMENTS)) {
                //获取财务报表
                FinancialStatementWsMessage req = (FinancialStatementWsMessage) messageClz;
                ftQotService.syncFinancialStatements(req);
            } else if (messageClz.getType().equals(MessageType.VALUATION_DETAIL)) {
                //获取个股/指数估值详情
                ValuationDetailWsMessage req = (ValuationDetailWsMessage) messageClz;
                ftQotService.syncValuationDetail(req);
            } else if (messageClz.getType().equals(MessageType.VALUATION_P_S_LIST)) {
                //获取板块/指数成分股估值列表
                ValuationPlateStockListWsMessage req = (ValuationPlateStockListWsMessage) messageClz;
                ftQotService.syncValuationPlateStockList(req);
            } else if (messageClz.getType().equals(MessageType.CO_ACTIONS_DIVIDEND)) {
                //分红派息
                CorporateActionsDividendsWsMessage req = (CorporateActionsDividendsWsMessage) messageClz;
                ftQotService.syncCorporateActionsDividend(req);
            } else if (messageClz.getType().equals(MessageType.CO_ACTIONS_BUYBACK)) {
                //回购
                CorporateActionsBuybackWsMessage req = (CorporateActionsBuybackWsMessage) messageClz;
                ftQotService.syncCorporateActionsBuyback(req);
            } else if (messageClz.getType().equals(MessageType.CO_ACTIONS_STOCK_SPLITS)) {
                //拆合股
                CorporateActionsStockSplitsWsMessage req = (CorporateActionsStockSplitsWsMessage) messageClz;
                ftQotService.syncCorporateActionsStockSplits(req);
            } else if (messageClz.getType().equals(MessageType.SHAREHOLDER_OVR)) {
                //持股统计
                ShareholderOvrWsMessage req = (ShareholderOvrWsMessage) messageClz;
                ftQotService.syncShareholderOvr(req);
            } else if (messageClz.getType().equals(MessageType.SHAREHOLDER_HOLDING_CHANGE)) {
                //持股变动
                ShareholderHoldingChangeWsMessage req = (ShareholderHoldingChangeWsMessage) messageClz;
                ftQotService.syncShareholderHoldingChange(req);
            } else if (messageClz.getType().equals(MessageType.SHAREHOLDER_HOLDER_DETAIL)) {
                //持股明细
                ShareholderHolderDetailWsMessage req = (ShareholderHolderDetailWsMessage) messageClz;
                ftQotService.syncShareholderHolderDetail(req);
            } else if (messageClz.getType().equals(MessageType.SHAREHOLDER_INSTITUTIONAL)) {
                //机构持股
                ShareholderInstitutionalWsMessage req = (ShareholderInstitutionalWsMessage) messageClz;
                ftQotService.syncShareholderInstitutional(req);
            } else if (messageClz.getType().equals(MessageType.INSIDER_HOLDER_LIST)) {
                //内部人员持股
                InsiderHolderListWsMessage req = (InsiderHolderListWsMessage) messageClz;
                ftQotService.syncInsiderHolderList(req);
            } else if (messageClz.getType().equals(MessageType.INSIDER_TRADE_LIST)) {
                //内部人员交易
                InsiderTradeListWsMessage req = (InsiderTradeListWsMessage) messageClz;
                ftQotService.syncInsiderTradeList(req);
            } else if (messageClz.getType().equals(MessageType.COMPANY_PROFILE)) {
                //公司概况
                CompanyProfileWsMessage req = (CompanyProfileWsMessage) messageClz;
                ftQotService.syncCompanyProfile(req);
            } else if (messageClz.getType().equals(MessageType.COMPANY_EXECUTIVES)) {
                //公司高管信息
                CompanyExecutivesWsMessage req = (CompanyExecutivesWsMessage) messageClz;
                ftQotService.syncCompanyExecutives(req);
            } else if (messageClz.getType().equals(MessageType.COMPANY_EXECUTIVE_BACKGROUND)) {
                //高管背景
                CompanyExecutiveBackgroungWsMessage req = (CompanyExecutiveBackgroungWsMessage) messageClz;
                ftQotService.syncCompanyExecutiveBackground(req);
            } else if (messageClz.getType().equals(MessageType.COMPANY_OP_EFFICIENCY)) {
                //经营效率
                CompanyOpEfficiencyWsMessage req = (CompanyOpEfficiencyWsMessage) messageClz;
                ftQotService.syncCompanyOperateEfficiency(req);
            } else if (messageClz.getType().equals(MessageType.TOP_TEN_BROKERS)) {
                //十大经纪商买卖数据
                TopTenBrokersWsMessage req = (TopTenBrokersWsMessage) messageClz;
                ftQotService.syncTopTenBrokersBuySell(req);
            } else if (messageClz.getType().equals(MessageType.DAILY_SHORT_VOLUME)) {
                //每日卖空成交
                DailyShortVolumeWsMessage req = (DailyShortVolumeWsMessage) messageClz;
                ftQotService.syncDailyShortVolume(req);
            } else if (messageClz.getType().equals(MessageType.SHORT_INTEREST)) {
                //空头持仓
                ShortInterestWsMessage req = (ShortInterestWsMessage) messageClz;
                ftQotService.syncShortInterest(req);
            } else if (messageClz.getType().equals(MessageType.INSTITUTION_LIST)) {
                //机构列表
                InstitutionListWsMessage req = (InstitutionListWsMessage) messageClz;
                ftQotService.syncInstitutionList(req);
            } else if (messageClz.getType().equals(MessageType.INSTITUTION_PROFILE)) {
                //机构概况
                InstitutionProfileWsMessage req = (InstitutionProfileWsMessage) messageClz;
                ftQotService.syncInstitutionProfile(req);
            } else if (messageClz.getType().equals(MessageType.INSTITUTION_DISTR)) {
                //机构持仓行业分布
                InstitutionDistributionWsMessage req = (InstitutionDistributionWsMessage) messageClz;
                ftQotService.syncInstitutionDistribution(req);
            } else if (messageClz.getType().equals(MessageType.INSTITUTION_HOLDING_CHANGE)) {
                //机构持仓变动
                InstitutionHoldingChangeWsMessage req = (InstitutionHoldingChangeWsMessage) messageClz;
                ftQotService.syncInstitutionHoldingChange(req);
            } else if (messageClz.getType().equals(MessageType.INSTITUTION_HOLDING_LIST)) {
                //机构持股列表
                InstitutionHoldingListWsMessage req = (InstitutionHoldingListWsMessage) messageClz;
                ftQotService.syncInstitutionHoldingList(req);
            } else if (messageClz.getType().equals(MessageType.ARK_FUND_HOLDING)) {
                //ark基金持仓
                ArkFundHoldingWsMessage req = (ArkFundHoldingWsMessage) messageClz;
                ftQotService.syncArkFundHolding(req);
            } else if (messageClz.getType().equals(MessageType.ARK_STOCK_DYNAMIC)) {
                //ARK个股交易动态
                ArkStockDynamicWsMessage req = (ArkStockDynamicWsMessage) messageClz;
                ftQotService.syncArkStockDynamic(req);
            } else if (messageClz.getType().equals(MessageType.ARK_ACTIVE_TRANSACTION)) {
                //ARK主动交易聚合
                ArkActiveTransactionWsMessage req = (ArkActiveTransactionWsMessage) messageClz;
                ftQotService.syncArkActiveTransaction(req);
            } else if (messageClz.getType().equals(MessageType.INDUSTRIAL_CHAIN_LIST)) {
                //产业链列表
                IndustrialChainListWsMessage req = (IndustrialChainListWsMessage) messageClz;
                ftQotService.syncIndustrialChainList(req);
            } else if (messageClz.getType().equals(MessageType.INDUSTRIAL_CHAIN_DETAIL)) {
                //产业链详情
                IndustrialChainDetailWsMessage req = (IndustrialChainDetailWsMessage) messageClz;
                ftQotService.syncIndustrialChainDetail(req);
            } else if (messageClz.getType().equals(MessageType.INDUSTRIAL_CHAIN_BY_PLATE)) {
                //板块关联产业链
                IndustrialChainByPlateWsMessage req = (IndustrialChainByPlateWsMessage) messageClz;
                ftQotService.syncIndustrialChainByPlate(req);
            } else if (messageClz.getType().equals(MessageType.INDUSTRIAL_PLATE_INFO)) {
                //产业板块信息
                IndustrialPlateInfoWsMessage req = (IndustrialPlateInfoWsMessage) messageClz;
                ftQotService.syncIndustrialPlateInfo(req);
            } else if (messageClz.getType().equals(MessageType.INDUSTRIAL_PLATE_STOCK)) {
                //产业板块成分股
                IndustrialPlateStockWsMessage req = (IndustrialPlateStockWsMessage) messageClz;
                ftQotService.syncIndustrialPlateStock(req);
            } else if (messageClz.getType().equals(MessageType.HEAT_MAP_DATA)) {
                //热力图数据
                HeatMapDataWsMessage req = (HeatMapDataWsMessage) messageClz;
                ftQotService.syncHeatMapData(req);
            } else if (messageClz.getType().equals(MessageType.RISE_FALL_DISTRIBUTION)) {
                //涨跌分布
                RiseFallDistributionWsMessage req = (RiseFallDistributionWsMessage) messageClz;
                ftQotService.syncRiseFallDistribution(req);
            } else if (messageClz.getType().equals(MessageType.SHORT_SELL_RANK)) {
                //卖空异动榜
                ShortSellRankWsMessage req = (ShortSellRankWsMessage) messageClz;
                ftQotService.syncShortSellRank(req);
            } else if (messageClz.getType().equals(MessageType.HIGH_DIVIDEND_SOE_RANK)) {
                //破净高股息国央企
                HighDividendSoeRankWsMessage req = (HighDividendSoeRankWsMessage) messageClz;
                ftQotService.syncHighDividendSoeRank(req);
            } else if (messageClz.getType().equals(MessageType.HOT_RANK)) {
                //热议榜
                HotListWsMessage req = (HotListWsMessage) messageClz;
                ftQotService.syncHotList(req);
            } else if (messageClz.getType().equals(MessageType.TOP_MOVERS_RANK)) {
                //领涨领跌榜
                TopMoversRankWsMessage req = (TopMoversRankWsMessage) messageClz;
                ftQotService.syncTopMoversRank(req);
            } else if (messageClz.getType().equals(MessageType.MARCO_INDIES)) {
                //宏观指标列表
                MarcoIndiesWsMessage req = (MarcoIndiesWsMessage) messageClz;
                ftQotService.syncMarcoIndies(req);
            } else if (messageClz.getType().equals(MessageType.MARCO_INDIES_HISTORY)) {
                //宏观指标历史数据
                MarcoIndiesHistoryWsMessage req = (MarcoIndiesHistoryWsMessage) messageClz;
                ftQotService.syncMarcoIndiesHistory(req);
            } else if (messageClz.getType().equals(MessageType.DIVIDEND_RANK)) {
                //股息排行
                DividendRankWsMessage req = (DividendRankWsMessage) messageClz;
                ftQotService.syncDividendRank(req);
            } else if (messageClz.getType().equals(MessageType.EARNINGS_CALENDAR)) {
                //财报日历
                EarningsCalendarWsMessage req = (EarningsCalendarWsMessage) messageClz;
                ftQotService.syncEarningsCalendar(req);
            } else if (messageClz.getType().equals(MessageType.DIVIDEND_CALENDAR)) {
                //派息日历
                DividendCalendarWsMessage req = (DividendCalendarWsMessage) messageClz;
                ftQotService.syncDividendCalendar(req);
            } else if (messageClz.getType().equals(MessageType.ECONOMIC_CALENDAR)) {
                //经济事件日历
                EconomicCalendarWsMessage req = (EconomicCalendarWsMessage) messageClz;
                ftQotService.syncEconomicCalendar(req);
            } else if (messageClz.getType().equals(MessageType.EARNINGS_BEAT_RANK)) {
                //盈利超预期排名
                EarningsBeatRankWsMessage req = (EarningsBeatRankWsMessage) messageClz;
                ftQotService.syncEarningsBeatRank(req);
            } else if (messageClz.getType().equals(MessageType.INDICATOR_LIST)) {
                //获取指标列表
                IndicatorListWsMessage req = (IndicatorListWsMessage) messageClz;
                ftQotService.syncIndicatorList(req);
            }
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
