package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.HighDividendSoeRankWsMessage;
import io.futakotome.trade.dto.ws.HotListWsMessage;
import io.futakotome.trade.dto.ws.ShortSellRankWsMessage;
import io.futakotome.trade.dto.ws.TopMoversRankWsMessage;
import io.futakotome.trade.event.HighDividendSoeRankUpdateEvent;
import io.futakotome.trade.event.HotListUpdateEvent;
import io.futakotome.trade.event.ShortSellRankUpdateEvent;
import io.futakotome.trade.event.TopMoversRankUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RankService {
    private final QuantxFutuWsService wsService;

    public RankService(QuantxFutuWsService wsService) {
        this.wsService = wsService;
    }

    @EventListener
    public void onTopMoversRankUpdate(TopMoversRankUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            TopMoversRankWsMessage message = new TopMoversRankWsMessage();
            message.setContent(event.getContent());
            wsService.sendTopMoversRank(message);
        }
    }

    @EventListener
    public void onHotListUpdate(HotListUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            HotListWsMessage message = new HotListWsMessage();
            message.setContent(event.getContent());
            wsService.sendHotList(message);
        }
    }

    @EventListener
    public void onHighDividendSoeRankUpdate(HighDividendSoeRankUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            HighDividendSoeRankWsMessage message = new HighDividendSoeRankWsMessage();
            message.setContent(event.getContent());
            wsService.sendHighDividendSoeRank(message);
        }
    }

    @EventListener
    public void onShortSellUpdate(ShortSellRankUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            ShortSellRankWsMessage message = new ShortSellRankWsMessage();
            message.setContent(event.getContent());
            wsService.sendShortSellRank(message);
        }
    }
}
