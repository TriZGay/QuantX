package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.ShareholderHolderDetailWsMessage;
import io.futakotome.trade.dto.ws.ShareholderHoldingChangeWsMessage;
import io.futakotome.trade.dto.ws.ShareholderInstitutionalWsMessage;
import io.futakotome.trade.dto.ws.ShareholderOvrWsMessage;
import io.futakotome.trade.event.ShareholderHolderDetailUpdateEvent;
import io.futakotome.trade.event.ShareholderHoldingChangeUpdateEvent;
import io.futakotome.trade.event.ShareholderInstitutionalUpdateEvent;
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
    public void onInstitutionalUpdate(ShareholderInstitutionalUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            ShareholderInstitutionalWsMessage message = new ShareholderInstitutionalWsMessage();
            message.setContent(event.getContent());
            wsService.sendShareholderInstitutional(message);
        }
    }

    @EventListener
    public void onHolderDetailUpdate(ShareholderHolderDetailUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            ShareholderHolderDetailWsMessage message = new ShareholderHolderDetailWsMessage();
            message.setContent(event.getContent());
            wsService.sendShareholderHolderDetail(message);
        }
    }

    @EventListener
    public void onHoldingChangeUpdate(ShareholderHoldingChangeUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            ShareholderHoldingChangeWsMessage message = new ShareholderHoldingChangeWsMessage();
            message.setContent(event.getContent());
            wsService.sendShareholderHoldingChange(message);
        }
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
