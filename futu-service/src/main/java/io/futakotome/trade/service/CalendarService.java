package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.DividendCalendarWsMessage;
import io.futakotome.trade.dto.ws.EarningsCalendarWsMessage;
import io.futakotome.trade.event.DividendCalendarUpdateEvent;
import io.futakotome.trade.event.EarningsCalendarUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CalendarService {
    private final QuantxFutuWsService wsService;

    public CalendarService(QuantxFutuWsService wsService) {
        this.wsService = wsService;
    }

    @EventListener
    public void onDividendCalendarUpdate(DividendCalendarUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            DividendCalendarWsMessage message = new DividendCalendarWsMessage();
            message.setContent(event.getContent());
            wsService.sendDividendCalendar(message);
        }
    }

    @EventListener
    public void onEarningsCalendarUpdate(EarningsCalendarUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            EarningsCalendarWsMessage message = new EarningsCalendarWsMessage();
            message.setContent(event.getContent());
            wsService.sendDividendRank(message);
        }
    }
}
