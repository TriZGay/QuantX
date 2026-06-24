package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.DailyShortVolumeWsMessage;
import io.futakotome.trade.dto.ws.ShortInterestWsMessage;
import io.futakotome.trade.dto.ws.TopTenBrokersWsMessage;
import io.futakotome.trade.event.DailyShortVolumeUpdateEvent;
import io.futakotome.trade.event.ShortInterestUpdateEvent;
import io.futakotome.trade.event.TopTenBrokersUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BrokersService {
    private final QuantxFutuWsService wsService;

    public BrokersService(QuantxFutuWsService wsService) {
        this.wsService = wsService;
    }

    @EventListener
    public void onShortInterestUpdate(ShortInterestUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            ShortInterestWsMessage message = new ShortInterestWsMessage();
            message.setContent(event.getContent());
            wsService.sendShortInterest(message);
        }
    }

    @EventListener
    public void onDailyShotVolumeUpdate(DailyShortVolumeUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            DailyShortVolumeWsMessage message = new DailyShortVolumeWsMessage();
            message.setContent(event.getContent());
            wsService.sendDailyShotVolume(message);
        }
    }

    @EventListener
    public void onTopTenBrokersUpdate(TopTenBrokersUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            TopTenBrokersWsMessage message = new TopTenBrokersWsMessage();
            message.setContent(event.getContent());
            wsService.sendTopTenBrokersBuySell(message);
        }
    }
}
