package io.futakotome.analyze.biz;

import io.futakotome.analyze.controller.vo.MetaRequest;
import io.futakotome.analyze.controller.vo.MetaResponse;
import io.futakotome.analyze.mapper.KLineMapper;
import io.futakotome.analyze.mapper.MetaDataMapper;

import java.util.List;
import java.util.stream.Collectors;

public class Meta {
    private final MetaDataMapper repository;

    public Meta(MetaDataMapper mapper) {
        this.repository = mapper;
    }

    public List<String> showTables() {
        return repository.tables();
    }

    public List<MetaResponse> hasDataCodes(MetaRequest request) {
        switch (request.getGranularity()) {
            case 1:
                return repository.kLineDistinctCodesCommon(KLineMapper.KL_MIN_1_ARC_TABLE_NAME)
                        .stream().map(metaDto -> new MetaResponse(metaDto.getMarket(), metaDto.getCode()))
                        .collect(Collectors.toList());
            case 2:
                return null;
        }
        return null;
    }
}
