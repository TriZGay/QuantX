package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.HeatMapDataWsMessage;
import io.futakotome.trade.dto.ws.RiseFallDistributionWsMessage;
import io.futakotome.trade.event.HeatMapDataUpdateEvent;
import io.futakotome.trade.event.RiseFallDistributionUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class StatisticsService {
    private final QuantxFutuWsService wsService;

    public StatisticsService(QuantxFutuWsService wsService) {
        this.wsService = wsService;
    }

    @EventListener
    public void onRiseFallUpdate(RiseFallDistributionUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            RiseFallDistributionWsMessage message = new RiseFallDistributionWsMessage();
            message.setContent(event.getContent());
            wsService.sendRiseFallDistribution(message);
        }
    }

    @EventListener
    public void onHeatMapDataUpdate(HeatMapDataUpdateEvent e) {
        if (Objects.nonNull(e.getContent())) {
            HeatMapDataWsMessage message = new HeatMapDataWsMessage();
            message.setContent(e.getContent());
            wsService.sendHeatMapData(message);
        }
    }
}
