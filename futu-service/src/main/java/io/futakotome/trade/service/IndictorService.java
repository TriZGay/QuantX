package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.IndicatorListWsMessage;
import io.futakotome.trade.event.IndicatorListUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class IndictorService {
    private final QuantxFutuWsService wsService;

    public IndictorService(QuantxFutuWsService wsService) {
        this.wsService = wsService;
    }

    @EventListener
    public void onIndicatorListUpdate(IndicatorListUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            IndicatorListWsMessage message = new IndicatorListWsMessage();
            message.setContent(event.getContent());
            wsService.sendIndicatorList(message);
        }
    }
}
