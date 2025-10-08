package io.futakotome.trade.domain;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.futakotome.trade.controller.vo.CommonSecurityRequest;
import io.futakotome.trade.controller.vo.SnapshotBaseResponse;
import io.futakotome.trade.controller.vo.SnapshotEquityResponse;
import io.futakotome.trade.domain.code.MarketType;
import io.futakotome.trade.domain.code.StockStatus;
import io.futakotome.trade.domain.code.StockType;
import io.futakotome.trade.dto.SnapshotBaseDto;
import io.futakotome.trade.dto.SnapshotEquityExDto;
import io.futakotome.trade.mapper.pg.*;
import io.futakotome.trade.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
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
        if (baseDtoService.saveOrUpdateBatch(snapshot.getBaseDtoList(), 1000)) {
            totalInsert += snapshot.getBaseDtoList().size();
        }
        if (Objects.nonNull(snapshot.getEquityExDtoList())
                && !snapshot.getEquityExDtoList().isEmpty()) {
            if (equityExDtoService.saveOrUpdateBatch(snapshot.getEquityExDtoList(), 1000)) {
                totalInsert += snapshot.getEquityExDtoList().size();
            }
        }
        //todo override other saveOrUpdateBatch
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

    @Transactional
    public SnapshotBaseResponse querySnapshot(CommonSecurityRequest securityRequest) {
        SnapshotBaseDto baseDto = baseDtoService.query()
                .eq("market", securityRequest.getMarket())
                .eq("code", securityRequest.getCode())
                .orderByDesc("update_time").list().stream().findFirst().orElseGet(SnapshotBaseDto::new);
        if (securityRequest.getSecurityType().equals(StockType.Eqty.getCode())) {
            //正股
            SnapshotEquityExDto equityExDto = equityExDtoService.getOne(Wrappers.query(new SnapshotEquityExDto())
                    .eq("market", securityRequest.getMarket())
                    .eq("code", securityRequest.getCode()));
            SnapshotEquityResponse snapshotEquityResponse = new SnapshotEquityResponse();
            setBaseResponse(snapshotEquityResponse, baseDto);
            if (Objects.nonNull(equityExDto)) {
                setEquityResponse(snapshotEquityResponse, equityExDto);
            }
            return snapshotEquityResponse;
        } else if (securityRequest.getSecurityType().equals(StockType.Future.getCode())) {
            //期货
        } else if (securityRequest.getSecurityType().equals(StockType.Index.getCode())) {
            //指数
        } else if (securityRequest.getSecurityType().equals(StockType.Drvt.getCode())) {
            //期权
        } else if (securityRequest.getSecurityType().equals(StockType.Plate.getCode())) {
            //板块
        } else if (securityRequest.getSecurityType().equals(StockType.Trust.getCode())) {
            //信托
        } else if (securityRequest.getSecurityType().equals(StockType.Warrant.getCode())) {
            //窝轮
        }
        return null;
    }

    private void setEquityResponse(SnapshotEquityResponse equityResponse, SnapshotEquityExDto equityExDto) {
        equityResponse.setIssuedShares(equityExDto.getIssuedShares());
        equityResponse.setIssuedMarketVal(equityExDto.getIssuedMarketVal());
        equityResponse.setNetAsset(equityExDto.getNetAsset());
        equityResponse.setNetProfit(equityExDto.getNetProfit());
        equityResponse.setEarningsPerShare(equityExDto.getEarningsPerShare());
        equityResponse.setOutstandingShares(equityExDto.getOutstandingShares());
        equityResponse.setOutstandingMarketVal(equityExDto.getOutstandingMarketVal());
        equityResponse.setNetAssetPerShare(equityExDto.getNetAssetPerShare());
        equityResponse.setEyRate(equityExDto.getEyRate());
        equityResponse.setPeRate(equityExDto.getPeRate());
        equityResponse.setPbRate(equityExDto.getPbRate());
        equityResponse.setPeTtmRate(equityExDto.getPeTtmRate());
        equityResponse.setDividendTtm(equityExDto.getDividendTtm());
        equityResponse.setDividendRatioTtm(equityExDto.getDividendRatioTtm());
        equityResponse.setDividendLfy(equityExDto.getDividendLfy());
        equityResponse.setDividendLfyRatio(equityExDto.getDividendLfyRatio());
    }

    private void setBaseResponse(SnapshotBaseResponse baseResponse, SnapshotBaseDto baseDto) {
        baseResponse.setMarket(MarketType.getNameByCode(baseDto.getMarket()));
        baseResponse.setCode(baseDto.getCode());
        baseResponse.setName(baseDto.getName());
        baseResponse.setType(StockType.getNameByCode(baseDto.getType()));
        baseResponse.setIsSuspend(baseDto.getIsSuspend() ? "已退市" : "在市");
        baseResponse.setListTime(baseDto.getListTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        baseResponse.setLotSize(baseDto.getLotSize());
        baseResponse.setPriceSpread(baseDto.getPriceSpread());
        baseResponse.setUpdateTime(baseDto.getUpdateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        baseResponse.setHighPrice(baseDto.getHighPrice());
        baseResponse.setOpenPrice(baseDto.getOpenPrice());
        baseResponse.setLowPrice(baseDto.getLowPrice());
        baseResponse.setLastClosePrice(baseDto.getLastClosePrice());
        baseResponse.setCurPrice(baseDto.getCurPrice());
        baseResponse.setVolume(baseDto.getVolume());
        baseResponse.setTurnover(baseDto.getTurnover());
        baseResponse.setTurnoverRate(baseDto.getTurnoverRate());
        baseResponse.setAskPrice(baseDto.getAskPrice());
        baseResponse.setBidPrice(baseDto.getBidPrice());
        baseResponse.setAskVol(baseDto.getAskVol());
        baseResponse.setBidVol(baseDto.getBidVol());
        baseResponse.setAmplitude(baseDto.getAmplitude());
        baseResponse.setAvgPrice(baseDto.getAvgPrice());
        baseResponse.setBidAskRatio(baseDto.getBidAskRatio());
        baseResponse.setVolumeRatio(baseDto.getVolumeRatio());
        baseResponse.setHighest52WeeksPrice(baseDto.getHighest52WeeksPrice());
        baseResponse.setLowest52WeeksPrice(baseDto.getLowest52WeeksPrice());
        baseResponse.setHighestHistoryPrice(baseDto.getHighestHistoryPrice());
        baseResponse.setLowestHistoryPrice(baseDto.getLowestHistoryPrice());
        baseResponse.setPrePrice(baseDto.getPrePrice());
        baseResponse.setPreHighPrice(baseDto.getPreHighPrice());
        baseResponse.setPreLowPrice(baseDto.getPreLowPrice());
        baseResponse.setPreVolume(baseDto.getPreVolume());
        baseResponse.setPreTurnover(baseDto.getPreTurnover());
        baseResponse.setPreChangeVal(baseDto.getPreChangeVal());
        baseResponse.setPreChangeRate(baseDto.getPreChangeRate());
        baseResponse.setPreAmplitude(baseDto.getPreAmplitude());
        baseResponse.setAfterPrice(baseDto.getAfterPrice());
        baseResponse.setAfterHighPrice(baseResponse.getAfterHighPrice());
        baseResponse.setAfterLowPrice(baseResponse.getAfterLowPrice());
        baseResponse.setAfterVolume(baseResponse.getAfterVolume());
        baseResponse.setAfterTurnover(baseResponse.getAfterTurnover());
        baseResponse.setAfterChangeVal(baseResponse.getAfterChangeVal());
        baseResponse.setAfterChangeRate(baseResponse.getAfterChangeRate());
        baseResponse.setAfterAmplitude(baseResponse.getAfterAmplitude());
        baseResponse.setSecStatus(StockStatus.getNameByCode(baseDto.getSecStatus()));
        baseResponse.setClosePrice5Minute(baseResponse.getClosePrice5Minute());

    }
}
