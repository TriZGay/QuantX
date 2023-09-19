package io.futakotome.rtck.mapper;

import com.clickhouse.jdbc.ClickHouseDataSource;
import io.futakotome.rtck.mapper.dto.RehabDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class RehabsMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(RehabsMapper.class);

    private final ClickHouseDataSource dataSource;

    public RehabsMapper(ClickHouseDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean insertOneRehab(RehabDto rehabDto) {
        LOGGER.info("复权因子入库:{}", rehabDto.toString());
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into t_t_rehabs " +
                            "select market,code,company_act_flag,fwd_factor_a,fwd_factor_b,bwd_factor_a,bwd_factor_b,split_base,split_ert,join_base,join_ert,bonus_base,bonus_ert,transfer_base,transfer_ert,allot_base,allot_ert,allot_price,add_base,add_ert,add_price,dividend,sp_dividend,time,add_time " +
                            "from input ('market Int8,code String,company_act_flag Int64,fwd_factor_a Float64,fwd_factor_b Float64,bwd_factor_a Float64,bwd_factor_b Float64,split_base Int32,split_ert Int32,join_base Int32,join_ert Int32,bonus_base Int32,bonus_ert Int32,transfer_base Int32,transfer_ert Int32,allot_base Int32,allot_ert Int32,allot_price Float64,add_base Int32,add_ert Int32,add_price Float64,dividend Float64,sp_dividend Float64,time Date,add_time DateTime64(3)')"
            )) {
                preparedStatement.setInt(1, rehabDto.getMarket());
                preparedStatement.setString(2, rehabDto.getCode());
                preparedStatement.setLong(3, rehabDto.getCompanyActFlag());
                preparedStatement.setDouble(4, rehabDto.getFwdFactorA());
                preparedStatement.setDouble(5, rehabDto.getFwdFactorB());
                preparedStatement.setDouble(6, rehabDto.getBwdFactorA());
                preparedStatement.setDouble(7, rehabDto.getBwdFactorB());
                preparedStatement.setInt(8, rehabDto.getSplitBase());
                preparedStatement.setInt(9, rehabDto.getSplitErt());
                preparedStatement.setInt(10, rehabDto.getJoinBase());
                preparedStatement.setInt(11, rehabDto.getJoinErt());
                preparedStatement.setInt(12, rehabDto.getBonusBase());
                preparedStatement.setInt(13, rehabDto.getBonusErt());
                preparedStatement.setInt(14, rehabDto.getTransferBase());
                preparedStatement.setInt(15, rehabDto.getTransferErt());
                preparedStatement.setInt(16, rehabDto.getAllotBase());
                preparedStatement.setInt(17, rehabDto.getAllotErt());
                preparedStatement.setDouble(18, rehabDto.getAllotPrice());
                preparedStatement.setInt(19, rehabDto.getAddBase());
                preparedStatement.setInt(20, rehabDto.getAddErt());
                preparedStatement.setDouble(21, rehabDto.getAddPrice());
                preparedStatement.setDouble(22, rehabDto.getDividend());
                preparedStatement.setDouble(23, rehabDto.getSpDividend());
                preparedStatement.setString(24, rehabDto.getTime());
                preparedStatement.setString(25, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException throwables) {
            LOGGER.error("复权因子数据插入失败", throwables);
            return false;
        }
    }
}
