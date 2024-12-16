package io.futakotome.analyze.biz;

import io.futakotome.analyze.controller.vo.KLineRequest;
import io.futakotome.analyze.controller.vo.KLineResponse;
import io.futakotome.analyze.mapper.KLineMapper;
import io.futakotome.analyze.mapper.dto.KLineDto;

import java.util.List;
import java.util.stream.Collectors;

public class KLine {
    private final KLineMapper repository;

    public KLine(KLineMapper repository) {
        this.repository = repository;
    }

    public int kLinesArchive(String fromTable, String toTable, String start, String end) {
        return repository.kLinesRawTransToArc(fromTable, toTable, start, end);
    }

    public List<KLineResponse> kLinesUseArc(KLineRequest kLineRequest) {
        switch (kLineRequest.getGranularity()) {
            case 1:
                return repository.queryKLineArchived(kLineRequest, KLineMapper.KL_MIN_1_ARC_TABLE_NAME)
                        .stream().map(this::dtoMapResp)
                        .collect(Collectors.toList());
            case 2:
                return null;
        }
        return null;
    }

    private KLineResponse dtoMapResp(KLineDto kLineDto) {
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
