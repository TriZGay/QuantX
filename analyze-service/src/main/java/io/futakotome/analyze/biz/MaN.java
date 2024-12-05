package io.futakotome.analyze.biz;

import io.futakotome.analyze.controller.vo.MaRequest;
import io.futakotome.analyze.controller.vo.MaResponse;
import io.futakotome.analyze.controller.vo.MaSpan;
import io.futakotome.analyze.mapper.KLineMapper;
import io.futakotome.analyze.mapper.MaNMapper;

import java.util.List;
import java.util.stream.Collectors;

public class MaN {
    private final MaNMapper repository;

    public MaN(MaNMapper repository) {
        this.repository = repository;
    }

    private Integer followingBySpan(Integer span) {
        if (span.equals(MaSpan.FIVE.getCode())) {
            return 4;
        } else if (span.equals(MaSpan.TEN.getCode())) {
            return 9;
        } else if (span.equals(MaSpan.TWENTY.getCode())) {
            return 19;
        } else if (span.equals(MaSpan.THIRTY.getCode())) {
            return 29;
        } else if (span.equals(MaSpan.SIXTY.getCode())) {
            return 59;
        } else if (span.equals(MaSpan.ONE_HUNDRED_TWENTY.getCode())) {
            return 119;
        }
        return 0;
    }

    public List<MaResponse> maNDataUseArc(MaRequest maRequest) {
        switch (maRequest.getGranularity()) {
            case 1:
                //1分k
                return repository.queryMaCommon(maRequest, KLineMapper.KL_MIN_1_ARC_TABLE_NAME, followingBySpan(maRequest.getSpan())).stream().map(maDto -> new MaResponse(maDto.getMarket(), maDto.getCode(),
                                maDto.getRehabType(), maDto.getMaValue(), maDto.getUpdateTime()))
                        .collect(Collectors.toList());
            case 2:
                return null;
        }
        return null;
    }
}
