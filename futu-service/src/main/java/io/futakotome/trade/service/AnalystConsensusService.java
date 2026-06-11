package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.AnalystConsensusWsMessage;
import io.futakotome.trade.event.AnalystConsensusUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AnalystConsensusService {
    private final QuantxFutuWsService wsService;

    public AnalystConsensusService(QuantxFutuWsService wsService) {
        this.wsService = wsService;
    }

    @EventListener
    public void onAnalystConsensusUpdate(AnalystConsensusUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            AnalystConsensusWsMessage message = new AnalystConsensusWsMessage();
            message.setContent(event.getContent());
            wsService.sendAnalystConsensus(message);
        }
    }
}
