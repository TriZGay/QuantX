package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.CorporateActionsDividendsWsMessage;
import io.futakotome.trade.event.CorporateActionsDividendUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CorporateActionsService {
    private final QuantxFutuWsService wsService;

    public CorporateActionsService(QuantxFutuWsService wsService) {
        this.wsService = wsService;
    }

    @EventListener
    public void onDividendUpdate(CorporateActionsDividendUpdateEvent event) {
        if (Objects.nonNull(event.getContents())) {
            CorporateActionsDividendsWsMessage message = new CorporateActionsDividendsWsMessage();
            message.setContents(event.getContents());
            wsService.sendCorporateActionsDividend(message);
        }
    }
}
