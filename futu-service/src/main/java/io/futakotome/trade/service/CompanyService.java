package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.CompanyProfileWsMessage;
import io.futakotome.trade.event.CompanyProfileUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CompanyService {
    private final QuantxFutuWsService wsService;

    public CompanyService(QuantxFutuWsService wsService) {
        this.wsService = wsService;
    }

    @EventListener
    public void onProfileUpdate(CompanyProfileUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            CompanyProfileWsMessage message = new CompanyProfileWsMessage();
            message.setContent(event.getContent());
            wsService.sendCompanyProfile(message);
        }
    }
}
