package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.*;
import io.futakotome.trade.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class IndustrialService {
    private final QuantxFutuWsService wsService;

    public IndustrialService(QuantxFutuWsService wsService) {
        this.wsService = wsService;
    }

    @EventListener
    public void onPlateStockUpdate(IndustrialPlateStockUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            IndustrialPlateStockWsMessage message = new IndustrialPlateStockWsMessage();
            message.setContent(event.getContent());
            wsService.sendIndustrialPlateStock(message);
        }
    }

    @EventListener
    public void onPlateInfoUpdate(IndustrialPlateInfoUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            IndustrialPlateInfoWsMessage message = new IndustrialPlateInfoWsMessage();
            message.setContent(event.getContent());
            wsService.sendIndustrialPlateInfo(message);
        }
    }

    @EventListener
    public void onChainByPlateUpdate(IndustrialChainByPlateUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            IndustrialChainByPlateWsMessage message = new IndustrialChainByPlateWsMessage();
            message.setContent(event.getContent());
            wsService.sendIndustrialChainByPlate(message);
        }
    }

    @EventListener
    public void onChainDetailUpdate(IndustrialChainDetailUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            IndustrialChainDetailWsMessage message = new IndustrialChainDetailWsMessage();
            message.setContent(event.getContent());
            wsService.sendIndustrialChainDetail(message);
        }
    }

    @EventListener
    public void onChainListUpdate(IndustrialChainListUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            IndustrialChainListWsMessage message = new IndustrialChainListWsMessage();
            message.setContent(event.getContent());
            wsService.sendIndustrialChainList(message);
        }
    }
}
