package io.futakotome.trade.utils.handler;

import io.futakotome.trade.utils.DatetimeUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateTimeTypeHandler extends BaseTypeHandler<String> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter);
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String datetimeWithTimeZone = rs.getString(columnName);
        return DatetimeUtil.convertDatetimeWithTimeZone(datetimeWithTimeZone);
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String datetimeWithTimeZone = rs.getString(columnIndex);
        return DatetimeUtil.convertDatetimeWithTimeZone(datetimeWithTimeZone);
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String datetimeWithTimeZone = cs.getString(columnIndex);
        return DatetimeUtil.convertDatetimeWithTimeZone(datetimeWithTimeZone);
    }
}
