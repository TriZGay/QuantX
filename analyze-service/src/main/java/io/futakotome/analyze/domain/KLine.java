package io.futakotome.analyze.domain;

import io.futakotome.analyze.controller.vo.KLineRequest;
import io.futakotome.analyze.controller.vo.KLineResponse;
import io.futakotome.analyze.mapper.KLineMapper;

import java.util.List;
import java.util.stream.Collectors;

public class KLine {
    private KLineMapper repository;

    public KLine(KLineMapper repository) {
        this.repository = repository;
    }

    public List<KLineResponse> kLinesUseArc(KLineRequest kLineRequest) {
        switch (kLineRequest.getGranularity()) {
            case 1:
                return repository.queryKLineArchived(kLineRequest, KLineMapper.KL_MIN_1_ARC_TABLE_NAME)
                        .stream().map(kLineDto -> new KLineResponse(
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
                                kLineDto.getUpdateTime()))
                        .collect(Collectors.toList());
            case 2:
                return null;
        }
        return null;
    }
}
