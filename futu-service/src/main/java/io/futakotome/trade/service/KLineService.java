package io.futakotome.trade.service;

import io.futakotome.trade.domain.code.KLType;
import io.futakotome.trade.dto.KLineMin1ArcDto;
import io.futakotome.trade.dto.message.KLMessageContent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KLineService {
    private final KLineMin1ArcService kLineMin1ArcService;

    public KLineService(KLineMin1ArcService kLineMin1ArcService) {
        this.kLineMin1ArcService = kLineMin1ArcService;
    }

    public int insertHistoryKLines(List<KLMessageContent> klMessageContents) {
        List<KLineMin1ArcDto> kLineMin1ArcDtos = new ArrayList<>();
        klMessageContents.forEach(klMessageContent -> {
            if (klMessageContent.getKlType().equals(KLType.MIN_1.getCode())) {
                kLineMin1ArcDtos.add(toKLineMin1ArcDto(klMessageContent));
            }
        });
        if (!klMessageContents.isEmpty()) {
            return kLineMin1ArcService.saveHistoryKLinesMin1(kLineMin1ArcDtos);
        }
        return 0;
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
