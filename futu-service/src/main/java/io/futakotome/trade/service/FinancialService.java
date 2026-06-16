package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.FinancialEarningMoveWsMessage;
import io.futakotome.trade.dto.ws.FinancialEarningPriceHistoryWsMessage;
import io.futakotome.trade.dto.ws.FinancialReWsMessage;
import io.futakotome.trade.event.FinancialEarningMoveUpdateEvent;
import io.futakotome.trade.event.FinancialEarningPriceHistoryUpdateEvent;
import io.futakotome.trade.event.FinancialRevenueBreakDownUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 财务报表等
 */
@Service
public class FinancialService {
    private final QuantxFutuWsService wsService;

    public FinancialService(QuantxFutuWsService wsService) {
        this.wsService = wsService;
    }

    @EventListener
    public void onFinancialEarningPriceHistoryUpdate(FinancialEarningPriceHistoryUpdateEvent event) {
        if (Objects.nonNull(event.getContents())) {
            FinancialEarningPriceHistoryWsMessage message = new FinancialEarningPriceHistoryWsMessage();
            message.setContents(event.getContents());
            wsService.sendFinancialEarningPriceHistory(message);
        }
    }

    @EventListener
    public void onFinancialEarningMoveUpdate(FinancialEarningMoveUpdateEvent event) {
        if (Objects.nonNull(event.getContents())) {
            FinancialEarningMoveWsMessage message = new FinancialEarningMoveWsMessage();
            message.setContents(event.getContents());
            wsService.sendFinancialEarningMove(message);
        }
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
