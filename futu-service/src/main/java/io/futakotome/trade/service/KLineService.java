package io.futakotome.trade.service;

import io.futakotome.trade.domain.code.KLType;
import io.futakotome.trade.dto.KLineDayArcDto;
import io.futakotome.trade.dto.KLineMin1ArcDto;
import io.futakotome.trade.dto.KLineMin1RawDto;
import io.futakotome.trade.dto.KLineMonthArcDto;
import io.futakotome.trade.dto.message.KLMessageContent;
import io.futakotome.trade.event.KLineUpdateEvent;
import io.futakotome.trade.utils.DatetimeUtil;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KLineService {
    private final KLineMin1ArcService kLineMin1ArcService;
    private final KLineMin1RawService kLineMin1RawService;
    private final KLineDayArcService kLineDayArcService;
    private final KLineMonthArcService kLineMonthArcService;

    public KLineService(KLineMin1ArcService kLineMin1ArcService, KLineMin1RawService kLineMin1RawService, KLineDayArcService kLineDayArcService, KLineMonthArcService kLineMonthArcService) {
        this.kLineMin1ArcService = kLineMin1ArcService;
        this.kLineMin1RawService = kLineMin1RawService;
        this.kLineDayArcService = kLineDayArcService;
        this.kLineMonthArcService = kLineMonthArcService;
    }

    public int insertHistoryKLines(List<KLMessageContent> klMessageContents) {
        List<KLineMin1ArcDto> kLineMin1ArcDtos = new ArrayList<>();
        List<KLineDayArcDto> kLineDayArcDtos = new ArrayList<>();
        List<KLineMonthArcDto> kLineMonthArcDtos = new ArrayList<>();
        klMessageContents.forEach(klMessageContent -> {
            if (klMessageContent.getKlType().equals(KLType.MIN_1.getCode())) {
                kLineMin1ArcDtos.add(toKLineMin1ArcDto(klMessageContent));
            }
            if (klMessageContent.getKlType().equals(KLType.DAY.getCode())) {
                kLineDayArcDtos.add(toKLineDayArcDto(klMessageContent));
            }
            if (klMessageContent.getKlType().equals(KLType.MONTH.getCode())) {
                kLineMonthArcDtos.add(toKLineMonthArcDto(klMessageContent));
            }
        });
        if (!kLineMin1ArcDtos.isEmpty()) {
            return kLineMin1ArcService.saveHistoryKLinesMin1(kLineMin1ArcDtos);
        }
        if (!kLineDayArcDtos.isEmpty()) {
            return kLineDayArcService.saveHistoryKLinesDay(kLineDayArcDtos);
        }
        if (!kLineMonthArcDtos.isEmpty()) {
            return kLineMonthArcService.saveHistoryKLinesMonth(kLineMonthArcDtos);
        }
        return 0;
    }

    @Async
    @EventListener
    public void onKLineUpdate(KLineUpdateEvent kLineUpdateEvent) {
        if (!kLineUpdateEvent.getContent().getBlank()) {
            if (kLineUpdateEvent.getContent().getKlType().equals(KLType.MIN_1.getCode())) {
                KLineMin1RawDto kLineMin1RawDto = toKLineMin1RawDto(kLineUpdateEvent.getContent());
                kLineMin1RawService.saveOne(kLineMin1RawDto);
            }
        }
    }

    private KLineMonthArcDto toKLineMonthArcDto(KLMessageContent content) {
        KLineMonthArcDto kLineMonthArcDto = new KLineMonthArcDto();
        kLineMonthArcDto.setMarket(content.getMarket());
        kLineMonthArcDto.setCode(content.getCode());
        kLineMonthArcDto.setRehabType(content.getRehabType());
        kLineMonthArcDto.setHighPrice(content.getHighPrice());
        kLineMonthArcDto.setOpenPrice(content.getOpenPrice());
        kLineMonthArcDto.setLowPrice(content.getLowPrice());
        kLineMonthArcDto.setClosePrice(content.getClosePrice());
        kLineMonthArcDto.setLastClosePrice(content.getLastClosePrice());
        kLineMonthArcDto.setVolume(content.getVolume());
        kLineMonthArcDto.setTurnover(content.getTurnover());
        kLineMonthArcDto.setTurnoverRate(content.getTurnoverRate());
        kLineMonthArcDto.setPe(content.getPe());
        kLineMonthArcDto.setChangeRate(content.getChangeRate());
        kLineMonthArcDto.setUpdateTime(content.getTime());
        return kLineMonthArcDto;
    }

    private KLineMin1RawDto toKLineMin1RawDto(KLMessageContent content) {
        KLineMin1RawDto kLineMin1RawDto = new KLineMin1RawDto();
        kLineMin1RawDto.setMarket(content.getMarket());
        kLineMin1RawDto.setCode(content.getCode());
        kLineMin1RawDto.setRehabType(content.getRehabType());
        kLineMin1RawDto.setHighPrice(content.getHighPrice());
        kLineMin1RawDto.setOpenPrice(content.getOpenPrice());
        kLineMin1RawDto.setLowPrice(content.getLowPrice());
        kLineMin1RawDto.setClosePrice(content.getClosePrice());
        kLineMin1RawDto.setLastClosePrice(content.getLastClosePrice());
        kLineMin1RawDto.setVolume(content.getVolume());
        kLineMin1RawDto.setTurnover(content.getTurnover());
        kLineMin1RawDto.setTurnoverRate(content.getTurnoverRate());
        kLineMin1RawDto.setPe(content.getPe());
        kLineMin1RawDto.setChangeRate(content.getChangeRate());
        kLineMin1RawDto.setUpdateTime(content.getTime());
        kLineMin1RawDto.setAddTime(DatetimeUtil.createNowWithTimeZone("Asia/Shanghai"));
        return kLineMin1RawDto;
    }

    private KLineDayArcDto toKLineDayArcDto(KLMessageContent klMessageContent) {
        KLineDayArcDto kLineDayArcDto = new KLineDayArcDto();
        kLineDayArcDto.setMarket(klMessageContent.getMarket());
        kLineDayArcDto.setCode(klMessageContent.getCode());
        kLineDayArcDto.setRehabType(klMessageContent.getRehabType());
        kLineDayArcDto.setHighPrice(klMessageContent.getHighPrice());
        kLineDayArcDto.setOpenPrice(klMessageContent.getOpenPrice());
        kLineDayArcDto.setLowPrice(klMessageContent.getLowPrice());
        kLineDayArcDto.setClosePrice(klMessageContent.getClosePrice());
        kLineDayArcDto.setLastClosePrice(klMessageContent.getLastClosePrice());
        kLineDayArcDto.setVolume(klMessageContent.getVolume());
        kLineDayArcDto.setTurnover(klMessageContent.getTurnover());
        kLineDayArcDto.setTurnoverRate(klMessageContent.getTurnoverRate());
        kLineDayArcDto.setPe(klMessageContent.getPe());
        kLineDayArcDto.setChangeRate(klMessageContent.getChangeRate());
        kLineDayArcDto.setUpdateTime(klMessageContent.getTime());
        return kLineDayArcDto;
    }

    private KLineMin1ArcDto toKLineMin1ArcDto(KLMessageContent klMessageContent) {
        KLineMin1ArcDto kLineMin1ArcDto = new KLineMin1ArcDto();
        kLineMin1ArcDto.setMarket(klMessageContent.getMarket());
        kLineMin1ArcDto.setCode(klMessageContent.getCode());
        kLineMin1ArcDto.setRehabType(klMessageContent.getRehabType());
        kLineMin1ArcDto.setHighPrice(klMessageContent.getHighPrice());
        kLineMin1ArcDto.setOpenPrice(klMessageContent.getOpenPrice());
        kLineMin1ArcDto.setLowPrice(klMessageContent.getLowPrice());
        kLineMin1ArcDto.setClosePrice(klMessageContent.getClosePrice());
        kLineMin1ArcDto.setLastClosePrice(klMessageContent.getLastClosePrice());
        kLineMin1ArcDto.setVolume(klMessageContent.getVolume());
        kLineMin1ArcDto.setTurnover(klMessageContent.getTurnover());
        kLineMin1ArcDto.setTurnoverRate(klMessageContent.getTurnoverRate());
        kLineMin1ArcDto.setPe(klMessageContent.getPe());
        kLineMin1ArcDto.setChangeRate(klMessageContent.getChangeRate());
        kLineMin1ArcDto.setUpdateTime(klMessageContent.getTime());
        return kLineMin1ArcDto;
    }
}
