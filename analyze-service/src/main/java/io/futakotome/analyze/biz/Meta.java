package io.futakotome.analyze.biz;

import io.futakotome.analyze.controller.vo.DbInfoResponse;
import io.futakotome.analyze.controller.vo.MetaRequest;
import io.futakotome.analyze.controller.vo.MetaResponse;
import io.futakotome.analyze.mapper.KLineMapper;
import io.futakotome.analyze.mapper.MetaDataMapper;
import io.futakotome.analyze.mapper.dto.AnaDatabaseInfoDto;

import java.util.List;
import java.util.stream.Collectors;

public class Meta {
    private final MetaDataMapper repository;

    public Meta(MetaDataMapper mapper) {
        this.repository = mapper;
    }

    public List<DbInfoResponse> dbInfo() {
        if (repository.dbInfo() != null) {
            return repository.dbInfo().stream().map(this::dbInfoDtoMapResponse)
                    .collect(Collectors.toList());
        } else {
            throw new IllegalStateException("查询数据库信息失败.");
        }
    }

    public List<String> showTables() {
        if (repository.tables() != null) {
            return repository.tables();
        } else {
            throw new IllegalStateException("查询数据库表格失败.");
        }
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

    private DbInfoResponse dbInfoDtoMapResponse(AnaDatabaseInfoDto dto) {
        DbInfoResponse response = new DbInfoResponse();
        response.setDatabase(dto.getDatabase());
        response.setTable(dto.getTable());
        response.setBytesOnDisk(dto.getBytesOnDisk());
        response.setSize(dto.getSize());
        response.setRows(dto.getRows());
        response.setCompressedRate(dto.getCompressedRate());
        response.setDataCompressedBytes(dto.getDataCompressedBytes());
        response.setDataUncompressedBytes(dto.getDataUncompressedBytes());
        return response;
    }
}
