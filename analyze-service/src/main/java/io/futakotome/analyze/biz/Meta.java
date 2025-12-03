package io.futakotome.analyze.biz;

import io.futakotome.analyze.controller.vo.DbInfoResponse;
import io.futakotome.analyze.controller.vo.MetaRequest;
import io.futakotome.analyze.controller.vo.MetaResponse;
import io.futakotome.analyze.controller.vo.TableInfoResponse;
import io.futakotome.analyze.mapper.KLineMapper;
import io.futakotome.analyze.mapper.MetaDataMapper;
import io.futakotome.analyze.mapper.dto.AnaDatabaseInfoDto;
import io.futakotome.analyze.mapper.dto.AnaTableInfoDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class Meta {
    private final MetaDataMapper repository;

    public Meta(MetaDataMapper mapper) {
        this.repository = mapper;
    }

    public String truncate(String tableName) {
        Integer rows = repository.truncate(tableName);
        if (Objects.nonNull(rows)) {
            return "成功删除条数:" + rows;
        } else {
            throw new RuntimeException("删除数据失败");
        }
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

    /**
     * MIN1(1, "1分K"),
     * DAY(2, "日K"),
     * WEEK(3, "周K"),
     * MONTH(4, "月K"),
     * YEAR(5, "年K"),
     * MIN5(6, "5分K"),
     * MIN15(7, "15分K"),
     * MIN30(8, "30分K"),
     * MIN60(9, "60分K"),
     * MIN3(10, "3分K"),
     * QUARTER(11, "季K");
     *
     * @param request
     * @return
     */
    public List<MetaResponse> hasDataCodes(MetaRequest request) {
        switch (request.getGranularity()) {
            case 1:
                return queryCodesBy(KLineMapper.KL_MIN_1_ARC_TABLE_NAME);
            case 2:
                return queryCodesBy(KLineMapper.KL_DAY_ARC_TABLE_NAME);
            case 3:
                return queryCodesBy(KLineMapper.KL_WEEK_ARC_TABLE_NAME);
            case 4:
                return queryCodesBy(KLineMapper.KL_MONTH_ARC_TABLE_NAME);
            case 5:
                return queryCodesBy(KLineMapper.KL_YEAR_ARC_TABLE_NAME);
            case 6:
                return queryCodesBy(KLineMapper.KL_MIN_5_ARC_TABLE_NAME);
            case 7:
                return queryCodesBy(KLineMapper.KL_MIN_15_ARC_TABLE_NAME);
            case 8:
                return queryCodesBy(KLineMapper.KL_MIN_30_ARC_TABLE_NAME);
            case 9:
                return queryCodesBy(KLineMapper.KL_MIN_60_TABLE_NAME);
            case 10:
                return queryCodesBy(KLineMapper.KL_MIN_3_ARC_TABLE_NAME);
            case 11:
                return queryCodesBy(KLineMapper.KL_QUARTER_TABLE_NAME);
        }
        throw new IllegalArgumentException("无此粒度");
    }

    private List<MetaResponse> queryCodesBy(String tableName) {
        return repository.kLineDistinctCodesCommon(tableName)
                .stream().map(metaDto -> new MetaResponse(metaDto.getMarket(), metaDto.getCode()))
                .collect(Collectors.toList());
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
