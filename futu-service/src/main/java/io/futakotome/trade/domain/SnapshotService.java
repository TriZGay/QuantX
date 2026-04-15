package io.futakotome.trade.domain;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.futakotome.trade.controller.vo.CommonSecurityRequest;
import io.futakotome.trade.controller.vo.SnapshotBaseResponse;
import io.futakotome.trade.controller.vo.SnapshotEquityResponse;
import io.futakotome.trade.controller.ws.QuantxFutuWsService;
import io.futakotome.trade.domain.code.MarketType;
import io.futakotome.trade.domain.code.StockStatus;
import io.futakotome.trade.domain.code.StockType;
import io.futakotome.trade.dto.*;
import io.futakotome.trade.dto.message.SnapshotContent;
import io.futakotome.trade.event.SnapshotUpdateEvent;
import io.futakotome.trade.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SnapshotService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SnapshotService.class);
    private final SnapshotBaseDtoService baseDtoService;
    private final SnapshotEquityExDtoService equityExDtoService;
    private final SnapshotFutureExDtoService futureExDtoService;
    private final SnapshotIndexExDtoService indexExDtoService;
    private final SnapshotOptionExDtoService optionExDtoService;
    private final SnapshotPlateExDtoService plateExDtoService;
    private final SnapshotTrustExDtoService trustExDtoService;
    private final SnapshotWarrantExDtoService warrantExDtoService;
    private final QuantxFutuWsService wsService;

    public SnapshotService(SnapshotBaseDtoService baseDtoService, SnapshotEquityExDtoService equityExDtoService, SnapshotFutureExDtoService futureExDtoService, SnapshotIndexExDtoService indexExDtoService, SnapshotOptionExDtoService optionExDtoService, SnapshotPlateExDtoService plateExDtoService, SnapshotTrustExDtoService trustExDtoService, SnapshotWarrantExDtoService warrantExDtoService, QuantxFutuWsService wsService) {
        this.baseDtoService = baseDtoService;
        this.equityExDtoService = equityExDtoService;
        this.futureExDtoService = futureExDtoService;
        this.indexExDtoService = indexExDtoService;
        this.optionExDtoService = optionExDtoService;
        this.plateExDtoService = plateExDtoService;
        this.trustExDtoService = trustExDtoService;
        this.warrantExDtoService = warrantExDtoService;
        this.wsService = wsService;
    }

    private Snapshot getSnapshot(List<SnapshotContent> snapshotContents) {
        Snapshot snapshot = new Snapshot();
        List<SnapshotBaseDto> baseDtoList = new ArrayList<>();
        List<SnapshotEquityExDto> equityExDtoList = new ArrayList<>();
        List<SnapshotOptionExDto> optionExDtoList = new ArrayList<>();
        List<SnapshotFutureExDto> futureExDtoList = new ArrayList<>();
        List<SnapshotIndexExDto> indexExDtoList = new ArrayList<>();
        List<SnapshotPlateExDto> plateExDtoList = new ArrayList<>();
        List<SnapshotTrustExDto> trustExDtoList = new ArrayList<>();
        List<SnapshotWarrantExDto> warrantExDtoList = new ArrayList<>();
        for (SnapshotContent snapshotContent : snapshotContents) {
            SnapshotBaseDto baseDto = getSnapshotBase(snapshotContent);
            baseDtoList.add(baseDto);
            if (Objects.nonNull(snapshotContent.getEquityExData())) {
                SnapshotEquityExDto equityExDto = getSnapshotEquityEx(snapshotContent);
                equityExDtoList.add(equityExDto);
            }
            if (Objects.nonNull(snapshotContent.getFutureExData())) {
                SnapshotFutureExDto futureExDto = getSnapshotFutureEx(snapshotContent);
                futureExDtoList.add(futureExDto);
            }
            if (Objects.nonNull(snapshotContent.getIndexExData())) {
                SnapshotIndexExDto indexExDto = getSnapshotIndexEx(snapshotContent);
                indexExDtoList.add(indexExDto);
            }
            if (Objects.nonNull(snapshotContent.getOptionExData())) {
                SnapshotOptionExDto optionExDto = getSnapshotOptionEx(snapshotContent);
                optionExDtoList.add(optionExDto);
            }
            if (Objects.nonNull(snapshotContent.getPlateExData())) {
                SnapshotPlateExDto plateExDto = getSnapshotPlateEx(snapshotContent);
                plateExDtoList.add(plateExDto);
            }
            if (Objects.nonNull(snapshotContent.getTrustExData())) {
                SnapshotTrustExDto trustExDto = getSnapshotTrustEx(snapshotContent);
                trustExDtoList.add(trustExDto);
            }
            if (Objects.nonNull(snapshotContent.getWarrantExData())) {
                SnapshotWarrantExDto warrantExDto = getSnapshotWarrantEx(snapshotContent);
                warrantExDtoList.add(warrantExDto);
            }
        }
        snapshot.setBaseDtoList(baseDtoList);
        snapshot.setEquityExDtoList(equityExDtoList);
        snapshot.setOptionExDtoList(optionExDtoList);
        snapshot.setFutureExDtoList(futureExDtoList);
        snapshot.setIndexExDtoList(indexExDtoList);
        snapshot.setPlateExDtoList(plateExDtoList);
        snapshot.setTrustExDtoList(trustExDtoList);
        snapshot.setWarrantExDtoList(warrantExDtoList);
        return snapshot;
    }

    private SnapshotWarrantExDto getSnapshotWarrantEx(SnapshotContent snapshotContent) {
        SnapshotWarrantExDto warrantExDto = new SnapshotWarrantExDto();
        warrantExDto.setOwnerMarket(snapshotContent.getWarrantExData().getOwner().getMarket());
        warrantExDto.setOwnerCode(snapshotContent.getWarrantExData().getOwner().getCode());
        warrantExDto.setConversionRate(snapshotContent.getWarrantExData().getConversionRate());
        warrantExDto.setWarrantType(snapshotContent.getWarrantExData().getWarrantType());
        warrantExDto.setStrikePrice(snapshotContent.getWarrantExData().getStrikePrice());
        warrantExDto.setMaturityTime(snapshotContent.getWarrantExData().getMaturityTime());
        warrantExDto.setEndTradeTime(snapshotContent.getWarrantExData().getEndTradeTime());
        warrantExDto.setRecoveryPrice(snapshotContent.getWarrantExData().getRecoveryPrice());
        warrantExDto.setStreetVolumn(snapshotContent.getWarrantExData().getStreetVolumn());
        warrantExDto.setIssueVolumn(snapshotContent.getWarrantExData().getIssueVolumn());
        warrantExDto.setStreetRate(snapshotContent.getWarrantExData().getStreetRate());
        warrantExDto.setDelta(snapshotContent.getWarrantExData().getDelta());
        warrantExDto.setImpliedVolatility(snapshotContent.getWarrantExData().getImpliedVolatility());
        warrantExDto.setPremium(snapshotContent.getWarrantExData().getPremium());
        warrantExDto.setMaturityTimestamp(LocalDateTime.parse(snapshotContent.getWarrantExData().getMaturityTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        warrantExDto.setEndTradeTimestamp(LocalDateTime.parse(snapshotContent.getWarrantExData().getEndTradeTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        warrantExDto.setLeverage(snapshotContent.getWarrantExData().getLeverage());
        warrantExDto.setIpop(snapshotContent.getWarrantExData().getIpop());
        warrantExDto.setBreakEventPoint(snapshotContent.getWarrantExData().getBreakEvenPoint());
        warrantExDto.setConversionPrice(snapshotContent.getWarrantExData().getConversionPrice());
        warrantExDto.setPriceRecoveryRatio(snapshotContent.getWarrantExData().getPriceRecoveryRatio());
        warrantExDto.setScore(snapshotContent.getWarrantExData().getScore());
        warrantExDto.setUpperStrikePrice(snapshotContent.getWarrantExData().getUpperStrikePrice());
        warrantExDto.setLowerStrikePrice(snapshotContent.getWarrantExData().getLowerStrikePrice());
        warrantExDto.setInlinePriceStatus(snapshotContent.getWarrantExData().getInLinePriceStatus());
        warrantExDto.setIssuerCode(snapshotContent.getWarrantExData().getIssuerCode());
        warrantExDto.setUpdateTime(LocalDateTime.parse(snapshotContent.getBasic().getUpdateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return warrantExDto;
    }

    private SnapshotTrustExDto getSnapshotTrustEx(SnapshotContent snapshotContent) {
        SnapshotTrustExDto trustExDto = new SnapshotTrustExDto();
        trustExDto.setMarket(snapshotContent.getBasic().getSecurity().getMarket());
        trustExDto.setCode(snapshotContent.getBasic().getSecurity().getCode());
        trustExDto.setDividendYield(snapshotContent.getTrustExData().getDividendYield());
        trustExDto.setAum(snapshotContent.getTrustExData().getAum());
        trustExDto.setOutstandingUnits(snapshotContent.getTrustExData().getOutstandingUnits());
        trustExDto.setNetAssetValue(snapshotContent.getTrustExData().getNetAssetValue());
        trustExDto.setPremium(snapshotContent.getTrustExData().getPremium());
        trustExDto.setAssetClass(snapshotContent.getTrustExData().getAssetClass());
        trustExDto.setUpdateTime(LocalDateTime.parse(snapshotContent.getBasic().getUpdateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return trustExDto;
    }

    private SnapshotPlateExDto getSnapshotPlateEx(SnapshotContent snapshotContent) {
        SnapshotPlateExDto plateExDto = new SnapshotPlateExDto();
        plateExDto.setMarket(snapshotContent.getBasic().getSecurity().getMarket());
        plateExDto.setCode(snapshotContent.getBasic().getSecurity().getCode());
        plateExDto.setRaiseCount(snapshotContent.getPlateExData().getRaiseCount());
        plateExDto.setFallCount(snapshotContent.getPlateExData().getFallCount());
        plateExDto.setEqualCount(snapshotContent.getPlateExData().getEqualCount());
        plateExDto.setUpdateTime(LocalDateTime.parse(snapshotContent.getBasic().getUpdateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return plateExDto;
    }

    private SnapshotOptionExDto getSnapshotOptionEx(SnapshotContent snapshotContent) {
        SnapshotOptionExDto optionExDto = new SnapshotOptionExDto();
        optionExDto.setOwnerMarket(snapshotContent.getOptionExData().getOwner().getMarket());
        optionExDto.setOwnerCode(snapshotContent.getOptionExData().getOwner().getCode());
        optionExDto.setOptionType(snapshotContent.getOptionExData().getType());
        optionExDto.setStrikeTime(snapshotContent.getOptionExData().getStrikeTime());
        optionExDto.setStrikePrice(snapshotContent.getOptionExData().getStrikePrice());
        optionExDto.setContractSize(snapshotContent.getOptionExData().getContractSize());
        optionExDto.setContractSizeFloat(snapshotContent.getOptionExData().getContractSizeFloat());
        optionExDto.setOpenInterest(snapshotContent.getOptionExData().getOpenInterest());
        optionExDto.setImpliedVolatility(snapshotContent.getOptionExData().getImpliedVolatility());
        optionExDto.setPremium(snapshotContent.getOptionExData().getPremium());
        optionExDto.setDelta(snapshotContent.getOptionExData().getDelta());
        optionExDto.setGamma(snapshotContent.getOptionExData().getGamma());
        optionExDto.setVega(snapshotContent.getOptionExData().getVega());
        optionExDto.setTheta(snapshotContent.getOptionExData().getTheta());
        optionExDto.setRho(snapshotContent.getOptionExData().getRho());
        optionExDto.setIndexOptionType(snapshotContent.getOptionExData().getIndexOptionType());
        optionExDto.setNetOpenInterest(snapshotContent.getOptionExData().getNetOpenInterest());
        optionExDto.setExpiryDateDistance(snapshotContent.getOptionExData().getExpiryDateDistance());
        optionExDto.setContractNominalValue(snapshotContent.getOptionExData().getContractNominalValue());
        optionExDto.setOwnerLotMultiplier(snapshotContent.getOptionExData().getOwnerLotMultiplier());
        optionExDto.setOptionAreaType(snapshotContent.getOptionExData().getOptionAreaType());
        optionExDto.setContractMultiplier(snapshotContent.getOptionExData().getContractMultiplier());
        optionExDto.setUpdateTime(LocalDateTime.parse(snapshotContent.getBasic().getUpdateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return optionExDto;
    }

    private SnapshotIndexExDto getSnapshotIndexEx(SnapshotContent snapshotContent) {
        SnapshotIndexExDto indexExDto = new SnapshotIndexExDto();
        indexExDto.setMarket(snapshotContent.getBasic().getSecurity().getMarket());
        indexExDto.setCode(snapshotContent.getBasic().getSecurity().getCode());
        indexExDto.setRaiseCount(snapshotContent.getIndexExData().getRaiseCount());
        indexExDto.setFallCount(snapshotContent.getIndexExData().getFallCount());
        indexExDto.setEqualCount(snapshotContent.getIndexExData().getEqualCount());
        indexExDto.setUpdateTime(LocalDateTime.parse(snapshotContent.getBasic().getUpdateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return indexExDto;
    }

    private SnapshotFutureExDto getSnapshotFutureEx(SnapshotContent snapshotContent) {
        SnapshotFutureExDto futureExDto = new SnapshotFutureExDto();
        futureExDto.setMarket(snapshotContent.getBasic().getSecurity().getMarket());
        futureExDto.setCode(snapshotContent.getBasic().getSecurity().getCode());
        futureExDto.setLastSettlePrice(snapshotContent.getFutureExData().getLastSettlePrice());
        futureExDto.setPosition(snapshotContent.getFutureExData().getPosition());
        futureExDto.setPositionChange(snapshotContent.getFutureExData().getPositionChange());
        futureExDto.setLastTradeTime(LocalDate.parse(snapshotContent.getFutureExData().getLastTradeTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        futureExDto.setIsMainContract(snapshotContent.getFutureExData().getMainContract());
        futureExDto.setUpdateTime(LocalDateTime.parse(snapshotContent.getBasic().getUpdateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return futureExDto;
    }

    private SnapshotEquityExDto getSnapshotEquityEx(SnapshotContent snapshotContent) {
        SnapshotEquityExDto equityExDto = new SnapshotEquityExDto();
        equityExDto.setMarket(snapshotContent.getBasic().getSecurity().getMarket());
        equityExDto.setCode(snapshotContent.getBasic().getSecurity().getCode());
        equityExDto.setIssuedShares(snapshotContent.getEquityExData().getIssuedShares());
        equityExDto.setIssuedMarketVal(snapshotContent.getEquityExData().getIssuedMarketVal());
        equityExDto.setNetAsset(snapshotContent.getEquityExData().getNetAsset());
        equityExDto.setNetProfit(snapshotContent.getEquityExData().getNetProfit());
        equityExDto.setEarningsPerShare(snapshotContent.getEquityExData().getEarningsPershare());
        equityExDto.setOutstandingShares(snapshotContent.getEquityExData().getOutstandingShares());
        equityExDto.setOutstandingMarketVal(snapshotContent.getEquityExData().getOutstandingMarketVal());
        equityExDto.setNetAssetPerShare(snapshotContent.getEquityExData().getNetAssetPershare());
        equityExDto.setEyRate(snapshotContent.getEquityExData().getEyRate());
        equityExDto.setPeRate(snapshotContent.getEquityExData().getPeRate());
        equityExDto.setPbRate(snapshotContent.getEquityExData().getPbRate());
        equityExDto.setPeTtmRate(snapshotContent.getEquityExData().getPeTTMRate());
        equityExDto.setDividendTtm(snapshotContent.getEquityExData().getDividendTTM());
        equityExDto.setDividendRatioTtm(snapshotContent.getEquityExData().getDividendRatioTTM());
        equityExDto.setDividendLfy(snapshotContent.getEquityExData().getDividendLFY());
        equityExDto.setDividendLfyRatio(snapshotContent.getEquityExData().getDividendLFYRatio());
        equityExDto.setUpdateTime(LocalDateTime.parse(snapshotContent.getBasic().getUpdateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return equityExDto;
    }

    private SnapshotBaseDto getSnapshotBase(SnapshotContent snapshotContent) {
        SnapshotBaseDto baseDto = new SnapshotBaseDto();
        baseDto.setMarket(snapshotContent.getBasic().getSecurity().getMarket());
        baseDto.setCode(snapshotContent.getBasic().getSecurity().getCode());
        baseDto.setName(snapshotContent.getBasic().getName());
        baseDto.setType(snapshotContent.getBasic().getType());
        baseDto.setIsSuspend(snapshotContent.getBasic().getSuspend());
        baseDto.setListTime(LocalDate.parse(snapshotContent.getBasic().getListTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        baseDto.setLotSize(snapshotContent.getBasic().getLotSize());
        baseDto.setPriceSpread(snapshotContent.getBasic().getPriceSpread());
        baseDto.setUpdateTime(LocalDateTime.parse(snapshotContent.getBasic().getUpdateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        baseDto.setHighPrice(snapshotContent.getBasic().getHighPrice());
        baseDto.setOpenPrice(snapshotContent.getBasic().getOpenPrice());
        baseDto.setLowPrice(snapshotContent.getBasic().getLowPrice());
        baseDto.setLastClosePrice(snapshotContent.getBasic().getLastClosePrice());
        baseDto.setCurPrice(snapshotContent.getBasic().getCurPrice());
        baseDto.setVolume(snapshotContent.getBasic().getVolume());
        baseDto.setTurnover(snapshotContent.getBasic().getTurnover());
        baseDto.setTurnoverRate(snapshotContent.getBasic().getTurnoverRate());
        baseDto.setAskPrice(snapshotContent.getBasic().getAskPrice());
        baseDto.setBidPrice(snapshotContent.getBasic().getBidPrice());
        baseDto.setAskVol(snapshotContent.getBasic().getAskVol());
        baseDto.setBidVol(snapshotContent.getBasic().getBidVol());
        baseDto.setAmplitude(snapshotContent.getBasic().getAmplitude());
        baseDto.setAvgPrice(snapshotContent.getBasic().getAvgPrice());
        baseDto.setBidAskRatio(snapshotContent.getBasic().getBidAskRatio());
        baseDto.setVolumeRatio(snapshotContent.getBasic().getVolumeRatio());
        baseDto.setHighest52WeeksPrice(snapshotContent.getBasic().getHighest52WeeksPrice());
        baseDto.setLowest52WeeksPrice(snapshotContent.getBasic().getLowest52WeeksPrice());
        baseDto.setHighestHistoryPrice(snapshotContent.getBasic().getHighestHistoryPrice());
        baseDto.setLowestHistoryPrice(snapshotContent.getBasic().getLowestHistoryPrice());
        if (Objects.nonNull(snapshotContent.getBasic().getPreMarket())) {
            baseDto.setPrePrice(snapshotContent.getBasic().getPreMarket().getPrice());
            baseDto.setPreHighPrice(snapshotContent.getBasic().getPreMarket().getHighPrice());
            baseDto.setPreLowPrice(snapshotContent.getBasic().getPreMarket().getLowPrice());
            baseDto.setPreVolume(snapshotContent.getBasic().getPreMarket().getVolume());
            baseDto.setPreTurnover(snapshotContent.getBasic().getPreMarket().getTurnover());
            baseDto.setPreChangeVal(snapshotContent.getBasic().getPreMarket().getChangeVal());
            baseDto.setPreChangeRate(snapshotContent.getBasic().getPreMarket().getChangeRate());
            baseDto.setPreAmplitude(snapshotContent.getBasic().getPreMarket().getAmplitude());
        }
        if (Objects.nonNull(snapshotContent.getBasic().getAfterMarket())) {
            baseDto.setAfterPrice(snapshotContent.getBasic().getAfterMarket().getPrice());
            baseDto.setAfterHighPrice(snapshotContent.getBasic().getAfterMarket().getHighPrice());
            baseDto.setAfterLowPrice(snapshotContent.getBasic().getAfterMarket().getLowPrice());
            baseDto.setAfterVolume(snapshotContent.getBasic().getAfterMarket().getVolume());
            baseDto.setAfterTurnover(snapshotContent.getBasic().getAfterMarket().getTurnover());
            baseDto.setAfterChangeVal(snapshotContent.getBasic().getAfterMarket().getChangeVal());
            baseDto.setAfterChangeRate(snapshotContent.getBasic().getAfterMarket().getChangeRate());
            baseDto.setAfterAmplitude(snapshotContent.getBasic().getAfterMarket().getAmplitude());
        }
        baseDto.setSecStatus(snapshotContent.getBasic().getSecStatus());
        baseDto.setClosePrice5Minute(snapshotContent.getBasic().getClosePrice5Minute());
        return baseDto;
    }

    @EventListener
    public void insertSnapshots(SnapshotUpdateEvent event) {
        Snapshot snapshot = getSnapshot(event.getContents());
        int insertRow = insertBatch(snapshot);
        String str = "同步快照数据,条数:" + insertRow;
        LOGGER.info(str);
        wsService.sendNotify(str);
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
        if (Objects.nonNull(snapshot.getPlateExDtoList()) &&
                !snapshot.getPlateExDtoList().isEmpty()) {
            if (plateExDtoService.saveOrUpdateBatch(snapshot.getPlateExDtoList(), 1000)) {
                totalInsert += snapshot.getPlateExDtoList().size();
            }
        }
        if (Objects.nonNull(snapshot.getFutureExDtoList())
                && !snapshot.getFutureExDtoList().isEmpty()) {
            if (futureExDtoService.saveOrUpdateBatch(snapshot.getFutureExDtoList(), 1000)) {
                totalInsert += snapshot.getFutureExDtoList().size();
            }
        }
        if (Objects.nonNull(snapshot.getIndexExDtoList())
                && !snapshot.getIndexExDtoList().isEmpty()) {
            if (indexExDtoService.saveOrUpdateBatch(snapshot.getIndexExDtoList(), 1000)) {
                totalInsert += snapshot.getIndexExDtoList().size();
            }
        }
        if (Objects.nonNull(snapshot.getOptionExDtoList())
                && !snapshot.getOptionExDtoList().isEmpty()) {
            if (optionExDtoService.saveOrUpdateBatch(snapshot.getOptionExDtoList(), 1000)) {
                totalInsert += snapshot.getOptionExDtoList().size();
            }
        }
        if (Objects.nonNull(snapshot.getTrustExDtoList()) &&
                !snapshot.getTrustExDtoList().isEmpty()) {
            if (trustExDtoService.saveOrUpdateBatch(snapshot.getTrustExDtoList(), 1000)) {
                totalInsert += snapshot.getTrustExDtoList().size();
            }
        }
        if (Objects.nonNull(snapshot.getWarrantExDtoList())
                && !snapshot.getWarrantExDtoList().isEmpty()) {
            if (warrantExDtoService.saveOrUpdateBatch(snapshot.getWarrantExDtoList(), 1000)) {
                totalInsert += snapshot.getWarrantExDtoList().size();
            }
        }
        return totalInsert;
    }

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
