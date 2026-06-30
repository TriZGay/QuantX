package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.IndustrialChainDetailWsMessage;
import io.futakotome.trade.dto.ws.IndustrialChainListWsMessage;
import io.futakotome.trade.event.IndustrialChainDetailUpdateEvent;
import io.futakotome.trade.event.IndustrialChainListUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class IndustrialService {
    private final QuantxFutuWsService wsService;

    public IndustrialService(QuantxFutuWsService wsService) {
        this.wsService = wsService;
    }

    @EventListener
    public void onChainDetailUpdate(IndustrialChainDetailUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            IndustrialChainDetailWsMessage message = new IndustrialChainDetailWsMessage();
            message.setContent(event.getContent());
            wsService.sendIndustrialChainDetail(message);
        }
    }

    @EventListener
    public void onChainListUpdate(IndustrialChainListUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            IndustrialChainListWsMessage message = new IndustrialChainListWsMessage();
            message.setContent(event.getContent());
            wsService.sendIndustrialChainList(message);
        }
    }
}
