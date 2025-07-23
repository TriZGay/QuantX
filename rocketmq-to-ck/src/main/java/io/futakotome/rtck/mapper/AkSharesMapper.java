package io.futakotome.rtck.mapper;

import io.futakotome.rtck.mapper.dto.StockUsRTDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AkSharesMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(AkSharesMapper.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AkSharesMapper(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public boolean insertUsRealTimeBatch(List<StockUsRTDto> dtos) {
        try {
            String sql = "insert into t_ak_us_rt" +
                    "(id,code,price,ratio,ratio_val,turnover,volume,amplitude,high,low,open,close,turnover_ratio,market_cap,pe_ratio,add_time)" +
                    "values (:id,:code,:price,:ratio,:ratioVal,:turnover,:volume,:amplitude,:high,:low,:open,:close,:turnoverRatio,:marketCap,:peRatio,:addTime)";
            int[] updateRows = namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(dtos));
            LOGGER.info("插入成功:条数{}", Arrays.stream(updateRows).sum());
            return true;
        } catch (Exception e) {
            LOGGER.error("插入美股实时数据出错.", e);
            return false;
        }
    }
}
