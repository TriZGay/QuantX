package io.futakotome.analyze.biz;

import io.futakotome.analyze.mapper.KLineMapper;
import io.futakotome.analyze.mapper.MaNMapper;
import io.futakotome.analyze.mapper.MacdMapper;
import io.futakotome.analyze.mapper.TradeDateMapper;
import io.futakotome.analyze.mapper.dto.*;
import io.futakotome.analyze.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Macd {
    private static final Logger LOGGER = LoggerFactory.getLogger(Macd.class);
    private KLineMapper kLineMapper;
    private MaNMapper maNMapper;
    private TradeDateMapper tradeDateMapper;
    private MacdMapper macdMapper;

    public Macd(MacdMapper macdMapper, KLineMapper kLineMapper, MaNMapper maNMapper, TradeDateMapper tradeDateMapper) {
        this.macdMapper = macdMapper;
        this.kLineMapper = kLineMapper;
        this.maNMapper = maNMapper;
        this.tradeDateMapper = tradeDateMapper;
    }

    public void calculate(String toTable, String fromTable, String startDateTime, String endDateTime) {
        //Clickhouse的开窗函数的边界值需要往后算120条数据才准确
        //因为香港市场的交易时间长,所以获取香港市场的交易时间也基本上覆盖大A
        //1分钟一条数据,2小时就120条数据,取startDateTime的前一天交易日作为开始边界日期
        LocalDate s = LocalDateTime.parse(startDateTime, DateUtils.DATE_TIME_FORMATTER).toLocalDate();
        List<TradeDateDto> tradeDates = tradeDateMapper.queryTradeDateByMarketPreceding(1, s, "1");
        TradeDateDto sdt;
        if (Objects.nonNull(tradeDates)) {
            if (!tradeDates.isEmpty()) {
                sdt = tradeDates.get(0);
            } else {
                LOGGER.error("{}->{}时间段:{}-{}归档MACD数据失败.交易日期缺失数据", fromTable, toTable,
                        startDateTime, endDateTime);
                return;
            }
        } else {
            LOGGER.error("{}->{}时间段:{}-{}归档MACD数据失败.交易日期为null", fromTable, toTable,
                    startDateTime, endDateTime);
            return;
        }
        List<KLineDto> kLineDtos = kLineMapper.queryKLineArchived(new KLineDto(fromTable, startDateTime, endDateTime));
        Map<CodeAndRehabTypeKey, List<KLineDto>> groupingKLineByCodeAndRehabType = kLineDtos.stream()
                .collect(Collectors.groupingBy(k -> new CodeAndRehabTypeKey(k.getRehabType(), k.getCode())));
        groupingKLineByCodeAndRehabType.keySet().forEach(key -> {
            List<KLineDto> kGroupByKey = groupingKLineByCodeAndRehabType.get(key);
            List<MaNDto> maNs = maNMapper.queryMa12AndMa26UseKArc(fromTable, key.getCode(), key.getRehabType(), sdt.getTime().atStartOfDay().format(DateUtils.DATE_TIME_FORMATTER), endDateTime);
            MaNDto initialMa = maNs.stream().filter(maNDto ->
                            LocalDateTime.parse(maNDto.getUpdateTime(), DateUtils.DATE_TIME_FORMATTER).isAfter(LocalDateTime.parse(startDateTime, DateUtils.DATE_TIME_FORMATTER)))
                    .findFirst().get();
            Ema.EmaInternal ema12 = new Ema.EmaInternal(12, initialMa.getMa_12());
            Ema.EmaInternal ema26 = new Ema.EmaInternal(26, initialMa.getMa_26());
            List<MacdDto> macdDtos = kGroupByKey.stream().map(kLineDto -> {
                Double ema12Val = ema12.calculate(kLineDto.getClosePrice());
                Double ema26Val = ema26.calculate(kLineDto.getClosePrice());
                Double dif = ema12Val - ema26Val;
                MacdDto macdDto = new MacdDto();
                macdDto.setDif(dif);
                macdDto.setMarket(kLineDto.getMarket());
                macdDto.setRehabType(kLineDto.getRehabType());
                macdDto.setCode(kLineDto.getCode());
                macdDto.setUpdateTime(kLineDto.getUpdateTime());
                return macdDto;
            }).collect(Collectors.toList());
            Ema.EmaInternal fastEma = new Ema.EmaInternal(9);
            List<MacdDto> macdInsert = macdDtos.stream().peek(macdDto -> {
                Double dea = fastEma.calculate(macdDto.getDif());
                macdDto.setDea(dea);
                Double macd = 2 * (macdDto.getDif() - macdDto.getDea());
                macdDto.setMacd(macd);
            }).collect(Collectors.toList());
            if (macdMapper.insertBatch(toTable, macdInsert)) {
                LOGGER.info("{}->{}时间段:{}-{}归档EMA数据成功.", fromTable, toTable, startDateTime, endDateTime);
            }
        });
    }
}
