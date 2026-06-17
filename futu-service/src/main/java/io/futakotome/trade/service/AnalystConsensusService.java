package io.futakotome.trade.service;

import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.dto.ws.AnalystConsensusWsMessage;
import io.futakotome.trade.dto.ws.MorningstarReportWsMessage;
import io.futakotome.trade.dto.ws.ResearchRatingSummaryWsMessage;
import io.futakotome.trade.event.AnalystConsensusUpdateEvent;
import io.futakotome.trade.event.MorningstarReportUpdateEvent;
import io.futakotome.trade.event.ResearchRatingSummaryUpdateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 分析师评级、研究报告等
 */
@Service
public class AnalystConsensusService {
    private final QuantxFutuWsService wsService;

    public AnalystConsensusService(QuantxFutuWsService wsService) {
        this.wsService = wsService;
    }

    @EventListener
    public void onResearchRatingSummaryUpdate(ResearchRatingSummaryUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            ResearchRatingSummaryWsMessage message = new ResearchRatingSummaryWsMessage();
            message.setContent(event.getContent());
            wsService.sendResearchRatingSummary(message);
        }
    }

    @EventListener
    public void onMorningstarReportUpdate(MorningstarReportUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            MorningstarReportWsMessage message = new MorningstarReportWsMessage();
            message.setContent(event.getContent());
            wsService.sendMorningstarReport(message);
        }
    }

    @EventListener
    public void onAnalystConsensusUpdate(AnalystConsensusUpdateEvent event) {
        if (Objects.nonNull(event.getContent())) {
            AnalystConsensusWsMessage message = new AnalystConsensusWsMessage();
            message.setContent(event.getContent());
            wsService.sendAnalystConsensus(message);
        }
    }
}
