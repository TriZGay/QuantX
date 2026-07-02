package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.ShortSellRankWsMessage;
import io.futakotome.trade.event.ShortSellRankUpdateEvent;
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
    public void onShortSellUpdate(ShortSellRankUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            ShortSellRankWsMessage message = new ShortSellRankWsMessage();
            message.setContent(event.getContent());
            wsService.sendShortSellRank(message);
        }
    }
}
