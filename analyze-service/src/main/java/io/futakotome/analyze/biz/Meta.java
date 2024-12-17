package io.futakotome.analyze.biz;

import io.futakotome.analyze.controller.vo.DbInfoResponse;
import io.futakotome.analyze.controller.vo.MetaRequest;
import io.futakotome.analyze.controller.vo.MetaResponse;
import io.futakotome.analyze.controller.vo.TableInfoResponse;
import io.futakotome.analyze.mapper.KLineMapper;
import io.futakotome.analyze.mapper.MetaDataMapper;
import io.futakotome.analyze.mapper.dto.AnaDatabaseInfoDto;
import io.futakotome.analyze.mapper.dto.AnaTableInfoDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Meta {
    private final MetaDataMapper repository;

    public Meta(MetaDataMapper mapper) {
        this.repository = mapper;
    }

    public List<TableInfoResponse> tableInfo(String tableName) {
        List<AnaTableInfoDto> tableInfo = repository.tableInfo(tableName);
        if (Objects.nonNull(tableInfo)) {
            return tableInfo.stream().map(this::tableInfoDtoMapResponse)
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("查询表信息失败.");
        }
    }

    public List<DbInfoResponse> dbInfo() {
        List<AnaDatabaseInfoDto> dbInfos = repository.dbInfo();
        if (Objects.nonNull(dbInfos)) {
            return dbInfos.stream().map(this::dbInfoDtoMapResponse)
                    .collect(Collectors.toList());
        } else {
            throw new IllegalStateException("查询数据库信息失败.");
        }
    }

    public List<String> showTables() {
        List<String> tables = repository.tables();
        if (Objects.nonNull(tables)) {
            return tables;
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

    private TableInfoResponse tableInfoDtoMapResponse(AnaTableInfoDto dto) {
        TableInfoResponse response = new TableInfoResponse();
        response.setCode(dto.getCode());
        response.setRehabType(dto.getRehabType());
        response.setMinTime(dto.getMinTime());
        response.setMaxTime(dto.getMaxTime());
        return response;
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
