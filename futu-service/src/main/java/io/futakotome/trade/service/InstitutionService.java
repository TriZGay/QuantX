package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.InstitutionListWsMessage;
import io.futakotome.trade.event.InstitutionListUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class InstitutionService {
    private final QuantxFutuWsService wsService;

    public InstitutionService(QuantxFutuWsService wsService) {
        this.wsService = wsService;
    }

    @EventListener
    public void onInstitutionListUpdate(InstitutionListUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            InstitutionListWsMessage message = new InstitutionListWsMessage();
            message.setContent(event.getContent());
            wsService.sendInstitutionList(message);
        }
    }
}
