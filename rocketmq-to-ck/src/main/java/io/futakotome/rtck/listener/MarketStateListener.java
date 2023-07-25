package io.futakotome.rtck.listener;

import io.futakotome.common.MessageCommon;
import io.futakotome.common.message.MarketStateMessage;
import io.futakotome.rtck.message.MarketStateWsMessage;
import io.futakotome.rtck.message.MessageService;
import io.futakotome.rtck.message.ReactiveWebSocketHandlerMapping;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = MessageCommon.MARKET_STATE_CONSUMER_GROUP, topic = MessageCommon.MARKET_STATE_TOPIC)
public class MarketStateListener implements RocketMQListener<MarketStateMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MarketStateListener.class);
    private final MessageService messageService;

    public MarketStateListener(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void onMessage(MarketStateMessage marketStateMessage) {
        MarketStateWsMessage wsMessage = new MarketStateWsMessage();
        wsMessage.setMarketHK(marketStateMessage.getMarketHK());
        wsMessage.setMarketUS(marketStateMessage.getMarketUS());
        wsMessage.setMarketSH(marketStateMessage.getMarketSH());
        wsMessage.setMarketSZ(marketStateMessage.getMarketSZ());
        wsMessage.setMarketHKFuture(marketStateMessage.getMarketHKFuture());
        wsMessage.setTime(marketStateMessage.getTime());
        wsMessage.setLocalTime(marketStateMessage.getLocalTime());
        wsMessage.setMarketUSFuture(marketStateMessage.getMarketUSFuture());
        wsMessage.setMarketSGFuture(marketStateMessage.getMarketSGFuture());
        wsMessage.setMarketJPFuture(marketStateMessage.getMarketJPFuture());
        LOGGER.info("WebSocket消息:{}", wsMessage.toString());
        messageService.onNext(wsMessage, ReactiveWebSocketHandlerMapping.MARKET_STATE_PATH);
    }
}
