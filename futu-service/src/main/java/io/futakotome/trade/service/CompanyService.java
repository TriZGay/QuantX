package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.CompanyExecutiveBackgroungWsMessage;
import io.futakotome.trade.dto.ws.CompanyExecutivesWsMessage;
import io.futakotome.trade.dto.ws.CompanyProfileWsMessage;
import io.futakotome.trade.event.CompanyExecutiveBackgroundUpdateEvent;
import io.futakotome.trade.event.CompanyExecutivesUpdateEvent;
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
    public void onExecutiveBackgroundUpdate(CompanyExecutiveBackgroundUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            CompanyExecutiveBackgroungWsMessage message = new CompanyExecutiveBackgroungWsMessage();
            message.setContent(event.getContent());
            wsService.sendCompanyExecutiveBackground(message);
        }
    }

    @EventListener
    public void onExecutivesUpdate(CompanyExecutivesUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            CompanyExecutivesWsMessage message = new CompanyExecutivesWsMessage();
            message.setContent(event.getContent());
            wsService.sendCompanyExecutives(message);
        }
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
