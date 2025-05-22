package io.futakotome.trade.controller.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.futakotome.trade.dto.message.CommonSecurity;
import io.futakotome.trade.dto.ws.*;
import io.futakotome.trade.service.FTQotService;
import io.futakotome.trade.service.FTTradeService;
import io.futakotome.trade.service.PlateDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

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

    public static final String EMA5_URI = "/ema5";

    private final FTQotService ftQotService;
    private final FTTradeService ftTradeService;
    private final PlateDtoService plateService;
    private final ObjectMapper objectMapper;

    public QuantxWsController(FTQotService ftQotService, FTTradeService ftTradeService, PlateDtoService plateService, ObjectMapper objectMapper) {
        this.ftQotService = ftQotService;
        this.ftTradeService = ftTradeService;
        this.plateService = plateService;
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
                if (connectWsMessage.isConnect()) {
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
                //todo 同步板块下股票数据 (优化代码
                StockInPlateWsMessage stockInPlateWsMessage = (StockInPlateWsMessage) messageClz;
                if (stockInPlateWsMessage.isAll()) {
                    List<CommonSecurity> allPlates = plateService.list().stream().map(plateDto -> {
                        CommonSecurity plateItem = new CommonSecurity();
                        plateItem.setMarket(plateDto.getMarket());
                        plateItem.setCode(plateDto.getCode());
                        return plateItem;
                    }).collect(Collectors.toList());
                    ftQotService.syncStockInPlate(allPlates);
                } else {
                    ftQotService.syncStockInPlate(stockInPlateWsMessage.getPlates());
                }
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
                ftQotService.syncCapitalFlow(capitalFlowWsMessage.getSecurity().getMarket(), capitalFlowWsMessage.getSecurity().getCode());
            } else if (messageClz.getType().equals(MessageType.REHABS)) {
                //查询复权因子
                RehabsWsMessage rehabsWsMessage = (RehabsWsMessage) messageClz;
                ftQotService.sendRehabRequest(rehabsWsMessage.getSecurity().getMarket(), rehabsWsMessage.getSecurity().getCode());
            } else if (messageClz.getType().equals(MessageType.SNAPSHOT)) {
                //查询快照数据
                SnapshotWsMessage snapshotWsMessage = (SnapshotWsMessage) messageClz;
                ftQotService.syncSnapshotData(snapshotWsMessage.getSecurities());
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
            }
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
