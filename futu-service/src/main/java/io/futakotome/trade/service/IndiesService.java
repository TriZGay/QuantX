package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.MarcoIndiesHistoryWsMessage;
import io.futakotome.trade.dto.ws.MarcoIndiesWsMessage;
import io.futakotome.trade.event.MarcoIndiesHistoryUpdateEvent;
import io.futakotome.trade.event.MarcoIndiesUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class IndiesService {
    private final QuantxFutuWsService wsService;

    public IndiesService(QuantxFutuWsService wsService) {
        this.wsService = wsService;
    }

    @EventListener
    public void onMarcoIndiesHistoryUpdate(MarcoIndiesHistoryUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            MarcoIndiesHistoryWsMessage message = new MarcoIndiesHistoryWsMessage();
            message.setContent(event.getContent());
            wsService.sendMarcoIndiesHistory(message);
        }
    }

    @EventListener
    public void onMarcoIndiesUpdate(MarcoIndiesUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            MarcoIndiesWsMessage message = new MarcoIndiesWsMessage();
            message.setContent(event.getContent());
            wsService.sendMarcoIndies(message);
        }
    }
}
