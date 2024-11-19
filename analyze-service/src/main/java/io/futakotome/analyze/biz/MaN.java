package io.futakotome.analyze.biz;

import io.futakotome.analyze.controller.vo.MaRequest;
import io.futakotome.analyze.controller.vo.MaResponse;
import io.futakotome.analyze.mapper.KLineMapper;
import io.futakotome.analyze.mapper.MaNMapper;

import java.util.List;
import java.util.stream.Collectors;

public class MaN {
    private final MaNMapper repository;

    public MaN(MaNMapper repository) {
        this.repository = repository;
    }

    public List<MaResponse> maNDataUseArc(MaRequest maRequest) {
        switch (maRequest.getGranularity()) {
            case 1:
                switch (maRequest.getSpan()) {
                    case 1:
                        //1分K ma5
                        return repository.queryMa5Common(maRequest, KLineMapper.KL_MIN_1_ARC_TABLE_NAME)
                                .stream().map(maDto -> new MaResponse(maDto.getMarket(), maDto.getCode(),
                                        maDto.getRehabType(), maDto.getMaValue(), maDto.getUpdateTime()))
                                .collect(Collectors.toList());
                    case 2:
                        //1分K ma10
                        return repository.queryMa10Common(maRequest, KLineMapper.KL_MIN_1_ARC_TABLE_NAME)
                                .stream().map(maDto -> new MaResponse(maDto.getMarket(), maDto.getCode(),
                                        maDto.getRehabType(), maDto.getMaValue(), maDto.getUpdateTime()))
                                .collect(Collectors.toList());
                    case 3:
                        //1分k ma20
                        return repository.queryMa20Common(maRequest, KLineMapper.KL_MIN_1_ARC_TABLE_NAME)
                                .stream().map(maDto -> new MaResponse(maDto.getMarket(), maDto.getCode(),
                                        maDto.getRehabType(), maDto.getMaValue(), maDto.getUpdateTime()))
                                .collect(Collectors.toList());
                    case 4:
                        //1分k ma30
                        return repository.queryMa30Common(maRequest, KLineMapper.KL_MIN_1_ARC_TABLE_NAME)
                                .stream().map(maDto -> new MaResponse(maDto.getMarket(), maDto.getCode(),
                                        maDto.getRehabType(), maDto.getMaValue(), maDto.getUpdateTime()))
                                .collect(Collectors.toList());
                    case 5:
                        //60
                        return repository.queryMa60Common(maRequest, KLineMapper.KL_MIN_1_ARC_TABLE_NAME)
                                .stream().map(maDto -> new MaResponse(maDto.getMarket(), maDto.getCode(),
                                        maDto.getRehabType(), maDto.getMaValue(), maDto.getUpdateTime()))
                                .collect(Collectors.toList());
                    case 6:
                        //120
                        return repository.queryMa120Common(maRequest, KLineMapper.KL_MIN_1_ARC_TABLE_NAME)
                                .stream().map(maDto -> new MaResponse(maDto.getMarket(), maDto.getCode(),
                                        maDto.getRehabType(), maDto.getMaValue(), maDto.getUpdateTime()))
                                .collect(Collectors.toList());
                }
            case 2:
                return null;
        }
        return null;
    }
}
