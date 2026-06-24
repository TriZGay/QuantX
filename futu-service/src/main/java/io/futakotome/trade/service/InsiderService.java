package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.InsiderHolderListWsMessage;
import io.futakotome.trade.event.InsiderHolderListUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class InsiderService {
    private final QuantxFutuWsService wsService;

    public InsiderService(QuantxFutuWsService wsService) {
        this.wsService = wsService;
    }

    @EventListener
    public void onHolderListUpdate(InsiderHolderListUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            InsiderHolderListWsMessage message = new InsiderHolderListWsMessage();
            message.setContent(event.getContent());
            wsService.sendInsiderHolderList(message);
        }
    }
}
