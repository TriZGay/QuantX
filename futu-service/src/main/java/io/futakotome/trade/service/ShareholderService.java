package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.ShareholderOvrWsMessage;
import io.futakotome.trade.event.ShareholderOvrUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ShareholderService {
    private final QuantxFutuWsService wsService;

    public ShareholderService(QuantxFutuWsService wsService) {
        this.wsService = wsService;
    }

    @EventListener
    public void onOvrUpdate(ShareholderOvrUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            ShareholderOvrWsMessage message = new ShareholderOvrWsMessage();
            message.setContent(event.getContent());
            wsService.sendShareholderOvr(message);
        }
    }
}
