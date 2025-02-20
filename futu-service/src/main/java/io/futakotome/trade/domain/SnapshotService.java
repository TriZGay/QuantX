package io.futakotome.trade.domain;

import io.futakotome.trade.mapper.*;
import io.futakotome.trade.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class SnapshotService {
    private final SnapshotBaseDtoService baseDtoService;
    private final SnapshotEquityExDtoService equityExDtoService;
    private final SnapshotFutureExDtoService futureExDtoService;
    private final SnapshotIndexExDtoService indexExDtoService;
    private final SnapshotOptionExDtoService optionExDtoService;
    private final SnapshotPlateExDtoService plateExDtoService;
    private final SnapshotTrustExDtoService trustExDtoService;
    private final SnapshotWarrantExDtoService warrantExDtoService;

    public SnapshotService(SnapshotBaseDtoService baseDtoService, SnapshotEquityExDtoService equityExDtoService, SnapshotFutureExDtoService futureExDtoService, SnapshotIndexExDtoService indexExDtoService, SnapshotOptionExDtoService optionExDtoService, SnapshotPlateExDtoService plateExDtoService, SnapshotTrustExDtoService trustExDtoService, SnapshotWarrantExDtoService warrantExDtoService) {
        this.baseDtoService = baseDtoService;
        this.equityExDtoService = equityExDtoService;
        this.futureExDtoService = futureExDtoService;
        this.indexExDtoService = indexExDtoService;
        this.optionExDtoService = optionExDtoService;
        this.plateExDtoService = plateExDtoService;
        this.trustExDtoService = trustExDtoService;
        this.warrantExDtoService = warrantExDtoService;
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertBatch(Snapshot snapshot) {
        int totalInsert = 0;
        SnapshotBaseDtoMapper baseDtoMapper = (SnapshotBaseDtoMapper) baseDtoService.getBaseMapper();
        totalInsert += baseDtoMapper.insertBatch(snapshot.getBaseDtoList());
        if (Objects.nonNull(snapshot.getEquityExDtoList())
                && !snapshot.getEquityExDtoList().isEmpty()) {
            SnapshotEquityExDtoMapper equityExDtoMapper = (SnapshotEquityExDtoMapper) equityExDtoService.getBaseMapper();
            totalInsert += equityExDtoMapper.insertBatch(snapshot.getEquityExDtoList());
        }
        if (Objects.nonNull(snapshot.getFutureExDtoList())
                && !snapshot.getFutureExDtoList().isEmpty()) {
            SnapshotFutureExDtoMapper futureExDtoMapper = (SnapshotFutureExDtoMapper) futureExDtoService.getBaseMapper();
            totalInsert += futureExDtoMapper.insertBatch(snapshot.getFutureExDtoList());
        }
        if (Objects.nonNull(snapshot.getIndexExDtoList())
                && !snapshot.getIndexExDtoList().isEmpty()) {
            SnapshotIndexExDtoMapper indexExDtoMapper = (SnapshotIndexExDtoMapper) indexExDtoService.getBaseMapper();
            totalInsert += indexExDtoMapper.insertBatch(snapshot.getIndexExDtoList());
        }
        if (Objects.nonNull(snapshot.getOptionExDtoList())
                && !snapshot.getOptionExDtoList().isEmpty()) {
            SnapshotOptionExDtoMapper optionExDtoMapper = (SnapshotOptionExDtoMapper) optionExDtoService.getBaseMapper();
            totalInsert += optionExDtoMapper.insertBatch(snapshot.getOptionExDtoList());
        }
        if (Objects.nonNull(snapshot.getPlateExDtoList()) &&
                !snapshot.getPlateExDtoList().isEmpty()) {
            SnapshotPlateExDtoMapper plateExDtoMapper = (SnapshotPlateExDtoMapper) plateExDtoService.getBaseMapper();
            totalInsert += plateExDtoMapper.insertBatch(snapshot.getPlateExDtoList());
        }
        if (Objects.nonNull(snapshot.getTrustExDtoList()) &&
                !snapshot.getTrustExDtoList().isEmpty()) {
            SnapshotTrustExDtoMapper trustExDtoMapper = (SnapshotTrustExDtoMapper) trustExDtoService.getBaseMapper();
            totalInsert += trustExDtoMapper.insertBatch(snapshot.getTrustExDtoList());
        }
        if (Objects.nonNull(snapshot.getWarrantExDtoList())
                && !snapshot.getWarrantExDtoList().isEmpty()) {
            SnapshotWarrantExDtoMapper warrantExDtoMapper = (SnapshotWarrantExDtoMapper) warrantExDtoService.getBaseMapper();
            totalInsert += warrantExDtoMapper.insertBatch(snapshot.getWarrantExDtoList());
        }
        return totalInsert;
    }
}
