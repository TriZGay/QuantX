package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.FinancialReWsMessage;
import io.futakotome.trade.event.FinancialRevenueBreakDownUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FinancialService {
    private final QuantxFutuWsService wsService;

    public FinancialService(QuantxFutuWsService wsService) {
        this.wsService = wsService;
    }

    @EventListener
    public void onFinancialRevenueBreakDownUpdate(FinancialRevenueBreakDownUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            FinancialReWsMessage message = new FinancialReWsMessage();
            message.setContent(event.getContent());
            wsService.sendFinancialRevenueBreakDown(message);
        }

    }
}
