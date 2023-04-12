package io.futakotome.stock.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.futu.openapi.FTAPI_Conn;
import com.futu.openapi.FTAPI_Conn_Qot;
import com.futu.openapi.FTSPI_Conn;
import com.futu.openapi.FTSPI_Qot;
import com.futu.openapi.pb.QotGetPlateSecurity;
import com.futu.openapi.pb.QotGetPlateSet;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.futakotome.stock.config.FutuConfig;
import io.futakotome.stock.domain.MarketAggregator;
import io.futakotome.stock.dto.PlateDto;
import io.futakotome.stock.dto.StockDto;
import io.futakotome.stock.mapper.PlateDtoMapper;
import io.futakotome.stock.mapper.StockDtoMapper;
import io.futakotome.stock.utils.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class QuotesService implements FTSPI_Conn, FTSPI_Qot, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuotesService.class);
    private static final Gson GSON = new Gson();
    private static final MarketAggregator market = new MarketAggregator();

    private final PlateDtoMapper plateMapper;
    private final StockDtoMapper stockMapper;
    private final FutuConfig futuConfig;

    private static final String clientID = "javaclient";

    public static final FTAPI_Conn_Qot qot = new FTAPI_Conn_Qot();


    public QuotesService(PlateDtoMapper plateMapper, StockDtoMapper stockMapper, FutuConfig futuConfig) {
        qot.setClientInfo(clientID, 1);
        qot.setConnSpi(this);
        qot.setQotSpi(this);
        this.futuConfig = futuConfig;
        this.plateMapper = plateMapper;
        this.stockMapper = stockMapper;
    }

//    @Scheduled(fixedRate = 24L, timeUnit = TimeUnit.HOURS)
//    public void syncPlateInfo() {
//        market.sendPlateInfoRequest();
//    }

    @Scheduled(fixedRate = 12L, timeUnit = TimeUnit.HOURS)
    public void syncStockInfo() {
        market.sendStockInfoRequest(plateMapper);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        qot.initConnect(futuConfig.getUrl(), futuConfig.getPort(), futuConfig.isEnableEncrypt());
    }

    @Override
    public void onInitConnect(FTAPI_Conn client, long errCode, String desc) {
        LOGGER.info("FUTU API 初始化连接 onInitConnect: ret=" + errCode + ",desc=" + desc + ",connID=" + client.getConnectID());
    }

    @Override
    public void onDisconnect(FTAPI_Conn client, long errCode) {
        LOGGER.info("FUTU API 关闭连接连接 onDisconnect: connID=" + client.getConnectID() + ",ret=" + errCode);
    }

    @Override
    @Transactional
    public void onReply_GetPlateSecurity(FTAPI_Conn client, int nSerialNo, QotGetPlateSecurity.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("查询股票信息失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("请求序列号:" + nSerialNo + "查询股票信息失败,code:" + rsp.getRetType()));
        } else {
            LOGGER.info("connID=" + client.getConnectID() + "查询股票信息...");
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp), FTGrpcReturnResult.class);
                Iterator<JsonElement> stockInfoIterator = ftGrpcReturnResult.getS2c().getAsJsonArray("staticInfoList").iterator();
                List<StockDto> newStocks = new ArrayList<>();
                String plateCode = (String) CacheManager.get(String.valueOf(nSerialNo));
                LOGGER.info("SeqNo=" + nSerialNo + "缓存的板块代码:" + plateCode);
                while (stockInfoIterator.hasNext()) {
                    JsonElement jsonElement = stockInfoIterator.next();
                    JsonObject basic = jsonElement.getAsJsonObject().get("basic").getAsJsonObject();
                    StockDto stockDto = new StockDto();
                    stockDto.setName(basic.get("name").getAsString());
                    stockDto.setLotSize(basic.get("lotSize").getAsInt());
                    stockDto.setStockType(basic.get("secType").getAsInt());
                    stockDto.setListingDate(LocalDate.parse(basic.get("listTime").getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    stockDto.setDelisting(basic.get("delisting").getAsBoolean() ? 1 : 0);
                    stockDto.setExchangeType(basic.get("exchType").getAsInt());
                    stockDto.setStockId(basic.get("id").getAsString());
                    stockDto.setPlateCode(plateCode);
                    stockDto.setCode(basic.get("security").getAsJsonObject().get("code").getAsString());
                    stockDto.setMarket(basic.get("security").getAsJsonObject().get("market").getAsInt());
                    newStocks.add(stockDto);
                }
                StockDto any = newStocks.get(0);
                QueryWrapper<StockDto> queryWrapper = Wrappers.query();
                if (any.getMarket() == 21 || any.getMarket() == 22) {
                    //沪深有可能有同一只股票
                    queryWrapper = queryWrapper.in("market", 21, 22);
                } else {
                    queryWrapper = queryWrapper.eq("market", any.getMarket());
                }
                List<StockDto> allStock = stockMapper.selectList(queryWrapper);
                newStocks.removeIf(allStock::contains);
                if (newStocks.size() > 0) {
                    int insertRow = stockMapper.insertBatch(newStocks);
                    LOGGER.info("插入条数:" + insertRow);
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询股票信息解析结果失败!", e);
            }
        }
    }

    @Override
    @Transactional
    public void onReply_GetPlateSet(FTAPI_Conn client, int nSerialNo, QotGetPlateSet.Response rsp) {
        if (rsp.getRetType() != 0) {
            LOGGER.error("查询板块信息失败:" + rsp.getRetMsg(),
                    new IllegalArgumentException("请求序列号:" + nSerialNo + "查询板块信息失败,code:" + rsp.getRetType()));
        } else {
            LOGGER.info("connID=" + client.getConnectID() + "查询板块信息...");
            try {
                FTGrpcReturnResult ftGrpcReturnResult = GSON.fromJson(JsonFormat.printer().print(rsp),
                        FTGrpcReturnResult.class);
                Iterator<JsonElement> plateInfosIterator = ftGrpcReturnResult.getS2c().getAsJsonArray("plateInfoList").iterator();
                List<PlateDto> newPlates = new ArrayList<>();
                while (plateInfosIterator.hasNext()) {
                    JsonElement jsonElement = plateInfosIterator.next();
                    PlateDto dto = new PlateDto();
                    dto.setName(jsonElement.getAsJsonObject().get("name").getAsString());
                    dto.setCode(jsonElement.getAsJsonObject().get("plate").getAsJsonObject().get("code").getAsString());
                    dto.setMarket(jsonElement.getAsJsonObject().get("plate").getAsJsonObject().get("market").getAsInt());
                    newPlates.add(dto);
                }
                List<PlateDto> allPlates = plateMapper.selectList(null);
                newPlates.removeIf(allPlates::contains);
                if (newPlates.size() > 0) {
                    int insertRow = plateMapper.insertBatch(newPlates);
                    LOGGER.info("插入条数:" + insertRow);
                }
            } catch (InvalidProtocolBufferException e) {
                LOGGER.error("查询板块信息解析结果失败!", e);
            }
        }

    }
}
