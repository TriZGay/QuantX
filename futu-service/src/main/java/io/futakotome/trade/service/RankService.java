package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.*;
import io.futakotome.trade.event.*;
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
    public void onEarningsBeatRankUpdate(EarningsBeatRankUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            EarningsBeatRankWsMessage message = new EarningsBeatRankWsMessage();
            message.setContent(event.getContent());
            wsService.sendEarningsBeatRank(message);
        }
    }

    @EventListener
    public void onDividendRankUpdate(DividendRankUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            DividendRankWsMessage message = new DividendRankWsMessage();
            message.setContent(event.getContent());
            wsService.sendDividendRank(message);
        }
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
