package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.TopTenBrokersWsMessage;
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
    public void onTopTenBrokersUpdate(TopTenBrokersUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            TopTenBrokersWsMessage message = new TopTenBrokersWsMessage();
            message.setContent(event.getContent());
            wsService.sendTopTenBrokersBuySell(message);
        }
    }
}
