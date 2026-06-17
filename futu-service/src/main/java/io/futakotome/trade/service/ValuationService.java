package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.ValuationDetailWsMessage;
import io.futakotome.trade.dto.ws.ValuationPlateStockListWsMessage;
import io.futakotome.trade.event.ValuationDetailUpdateEvent;
import io.futakotome.trade.event.ValuationPlateStockListUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ValuationService {
    private final QuantxFutuWsService wsService;

    public ValuationService(QuantxFutuWsService wsService) {
        this.wsService = wsService;
    }

    @EventListener
    public void onValuationPlateStockListUpdate(ValuationPlateStockListUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            ValuationPlateStockListWsMessage message = new ValuationPlateStockListWsMessage();
            message.setContent(event.getContent());
            wsService.sendValuationPlateStockList(message);
        }
    }

    @EventListener
    public void onValuationDetailUpdate(ValuationDetailUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            ValuationDetailWsMessage message = new ValuationDetailWsMessage();
            message.setContent(event.getContent());
            wsService.sendValuationDetail(message);
        }
    }
}

