package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.*;
import io.futakotome.trade.event.*;
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
    public void onArkStockDynamicUpdate(ArkStockDynamicUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            ArkStockDynamicWsMessage message = new ArkStockDynamicWsMessage();
            message.setContent(event.getContent());
            wsService.sendArkStockDynamic(message);
        }
    }

    @EventListener
    public void onArkFundHoldingUpdate(ArkFundHoldingUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            ArkFundHoldingWsMessage message = new ArkFundHoldingWsMessage();
            message.setContent(event.getContent());
            wsService.sendArkFundHolding(message);
        }
    }

    @EventListener
    public void onInstitutionHoldingListUpdate(InstitutionHoldingListUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            InstitutionHoldingListWsMessage message = new InstitutionHoldingListWsMessage();
            message.setContent(event.getContent());
            wsService.sendInstitutionHoldingList(message);
        }
    }

    @EventListener
    public void onInstitutionHoldingChangeUpdate(InstitutionHoldingChangeUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            InstitutionHoldingChangeWsMessage message = new InstitutionHoldingChangeWsMessage();
            message.setContent(event.getContent());
            wsService.sendInstitutionHoldingChange(message);
        }
    }

    @EventListener
    public void onInstitutionDistributionUpdate(InstitutionDistributionUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            InstitutionDistributionWsMessage message = new InstitutionDistributionWsMessage();
            message.setContent(event.getContent());
            wsService.sendInstitutionDistribution(message);
        }
    }

    @EventListener
    public void onInstitutionProfileUpdate(InstitutionProfileUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            InstitutionProfileWsMessage message = new InstitutionProfileWsMessage();
            message.setContent(event.getContent());
            wsService.sendInstitutionProfile(message);
        }
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
