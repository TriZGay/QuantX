package io.futakotome.analyze.mapper;

import io.futakotome.analyze.mapper.dto.DataQualityDto;
import io.futakotome.analyze.mapper.dto.KLineRepeatDetailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Component
public class DataQualityMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataQualityMapper.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DataQualityMapper(@Qualifier("offlineNamedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<DataQualityDto> list(LocalDate startDate, LocalDate endDate) {
        try {
            String sql = "select check_date,kline_has_repeat from public.t_data_quality where check_date >= :startDate and check_date <= :endDate";
            return namedParameterJdbcTemplate.query(sql, new HashMap<>() {{
                put("startDate", startDate);
                put("endDate", endDate);
            }}, new BeanPropertyRowMapper<>(DataQualityDto.class));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    public boolean insertRepeatDetails(List<KLineRepeatDetailDto> detailDtos) {
        try {
            String sql = "insert into public.t_kl_repeat_details(check_date,code,rehab_type,update_time) values (:checkDate,:code,:rehabType,:updateTime) on conflict (check_date,code,rehab_type,update_time)" +
                    " do update set check_date=excluded.check_date,code=excluded.code,rehab_type=excluded.rehab_type,update_time=excluded.update_time";
            int[] insertRows = namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(detailDtos));
            return insertRows.length != 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
    }

    public boolean updateKlineRepeatStatus(LocalDate checkDate, boolean klineHasRepeat) {
        try {
            String sql = "update public.t_data_quality set kline_has_repeat=:klineHasRepeat where check_date=:checkDate";
            int updateRows = namedParameterJdbcTemplate.update(sql, new HashMap<>() {{
                put("klineHasRepeat", klineHasRepeat);
                put("checkDate", checkDate);
            }});
            return updateRows > 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
    }

    public DataQualityDto queryOneBy(LocalDate checkDate) {
        try {
            String sql = "select check_date,kline_has_repeat from public.t_data_quality where check_date = :checkDate";
            return namedParameterJdbcTemplate.queryForObject(sql, new HashMap<>() {{
                put("checkDate", checkDate);
            }}, new BeanPropertyRowMapper<>(DataQualityDto.class));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    public boolean insertOne(DataQualityDto dto) {
        try {
            String sql = "insert into public.t_data_quality(check_date, kline_has_repeat) values (:checkDate, :klineHasRepeat)";
            int insertRow = namedParameterJdbcTemplate.update(sql, new HashMap<>() {{
                put("checkDate", dto.getCheckDate());
                put("klineHasRepeat", dto.isKlineHasRepeat());
            }});
            return insertRow > 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
    }
}
