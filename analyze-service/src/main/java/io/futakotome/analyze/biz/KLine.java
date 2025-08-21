package io.futakotome.analyze.biz;

import io.futakotome.analyze.controller.vo.KLineRepeatResponse;
import io.futakotome.analyze.controller.vo.KLineRequest;
import io.futakotome.analyze.controller.vo.KLineResponse;
import io.futakotome.analyze.mapper.KLineMapper;
import io.futakotome.analyze.mapper.dto.KLineDto;
import io.futakotome.analyze.mapper.dto.KLineRepeatDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class KLine {
    private static final Logger LOGGER = LoggerFactory.getLogger(KLine.class);
    private final KLineMapper repository;

    public KLine(KLineMapper repository) {
        this.repository = repository;
    }

    public void kLinesArchive(String fromTable, String toTable, String start, String end) {
        List<KLineDto> toInsertDtos = repository.prefetchToInsert(fromTable, start, end)
                .stream().distinct().collect(Collectors.toList());
        if (repository.insertBatch(toTable, toInsertDtos)) {
            LOGGER.info("{}->{}时间段:{}-{}归档K线数据成功.", fromTable, toTable,
                    start, end);
        }
    }

    public List<KLineRepeatResponse> kLinesRepeat(String start, String end, String table) {
        return repository.queryKLineArchivedRepeated(start, end, table)
                .stream().map(this::dtoMapRepeatResponse).collect(Collectors.toList());
    }

    public List<KLineResponse> kLinesUseArc(KLineRequest kLineRequest) {
        switch (kLineRequest.getGranularity()) {
            case 1:
                return repository.queryKLineArchived(new KLineDto(KLineMapper.KL_MIN_1_ARC_TABLE_NAME, kLineRequest.getCode(), kLineRequest.getRehabType(),
                                kLineRequest.getStart(), kLineRequest.getEnd()))
                        .stream().distinct().map(KLine::dto2Resp)
                        .collect(Collectors.toList());
            case 2:
                return null;
        }
        return null;
    }

    private KLineRepeatResponse dtoMapRepeatResponse(KLineRepeatDto kLineRepeatDto) {
        KLineRepeatResponse kLineRepeatResponse = new KLineRepeatResponse();
        kLineRepeatResponse.setMarket(kLineRepeatDto.getMarket());
        kLineRepeatResponse.setCode(kLineRepeatDto.getCode());
        kLineRepeatResponse.setRehabType(kLineRepeatDto.getRehabType());
        kLineRepeatResponse.setHighPrice(kLineRepeatDto.getHighPrice());
        kLineRepeatResponse.setLowPrice(kLineRepeatDto.getLowPrice());
        kLineRepeatResponse.setOpenPrice(kLineRepeatDto.getOpenPrice());
        kLineRepeatResponse.setClosePrice(kLineRepeatDto.getClosePrice());
        kLineRepeatResponse.setLastClosePrice(kLineRepeatDto.getLastClosePrice());
        kLineRepeatResponse.setVolume(kLineRepeatDto.getVolume());
        kLineRepeatResponse.setTurnover(kLineRepeatDto.getTurnover());
        kLineRepeatResponse.setTurnoverRate(kLineRepeatDto.getTurnoverRate());
        kLineRepeatResponse.setPe(kLineRepeatDto.getPe());
        kLineRepeatResponse.setChangeRate(kLineRepeatDto.getChangeRate());
        kLineRepeatResponse.setUpdateTime(kLineRepeatDto.getUpdateTime());
        kLineRepeatResponse.setRepeat(kLineRepeatDto.getRepeat());
        return kLineRepeatResponse;
    }

    public static KLineResponse dto2Resp(KLineDto kLineDto) {
        return new KLineResponse(
                kLineDto.getMarket(),
                kLineDto.getCode(),
                kLineDto.getRehabType(),
                kLineDto.getHighPrice() == 0D ? "-"
                        : String.valueOf(kLineDto.getHighPrice()),
                kLineDto.getOpenPrice() == 0D ? "-"
                        : String.valueOf(kLineDto.getOpenPrice()),
                kLineDto.getLowPrice() == 0D ? "-"
                        : String.valueOf(kLineDto.getLowPrice()),
                kLineDto.getClosePrice() == 0D ? "-"
                        : String.valueOf(kLineDto.getClosePrice()),
                kLineDto.getLastClosePrice(),
                kLineDto.getVolume(),
                kLineDto.getTurnover(),
                kLineDto.getTurnoverRate(),
                kLineDto.getPe(),
                kLineDto.getChangeRate(),
                kLineDto.getUpdateTime());
    }
}
