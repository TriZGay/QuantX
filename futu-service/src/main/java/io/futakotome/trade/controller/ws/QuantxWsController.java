package io.futakotome.trade.controller.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.futakotome.trade.dto.message.CommonSecurity;
import io.futakotome.trade.dto.ws.*;
import io.futakotome.trade.service.FTQotService;
import io.futakotome.trade.service.PlateDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class QuantxWsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuantxWsController.class);
    public static final String NOTIFY_URI = "/notify";
    public static final String HISTORY_KLINE_QUOTA_URI = "/history_k_quo";
    public static final String MARKET_STATE_URI = "/market_state";
    public static final String CAPITAL_DISTR_URI = "/capital_distr";

    private final FTQotService ftQotService;
    private final PlateDtoService plateService;
    private final ObjectMapper objectMapper;

    public QuantxWsController(FTQotService ftQotService, PlateDtoService plateService, ObjectMapper objectMapper) {
        this.ftQotService = ftQotService;
        this.plateService = plateService;
        this.objectMapper = objectMapper;
    }

    //这里的`/notify`是stomp client给server发送数据的destination,是有/quantx/ft前缀拼接的
    @MessageMapping(NOTIFY_URI)
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
                } else {
                    ftQotService.disconnect();
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
            }
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
