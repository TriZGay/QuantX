package io.futakotome.quantx.fomatters;

import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.connector.jdbc.JdbcInputFormat;
import org.apache.flink.connector.jdbc.JdbcRowOutputFormat;

public class JdbcFormatter {

    public static JdbcInputFormat inputFromClickhouse(ParameterTool parameter, String query, RowTypeInfo rowTypeInfo) {
        return JdbcInputFormat.buildJdbcInputFormat()
                .setDrivername(parameter.getRequired("ck.driverName"))
                .setUsername(parameter.getRequired("ck.username"))
                .setPassword(parameter.getRequired("ck.pwd"))
                .setDBUrl(parameter.getRequired("ck.url"))
                .setQuery(query)
                .setRowTypeInfo(rowTypeInfo)
                .finish();
    }

    public static JdbcInputFormat inputFromPg(ParameterTool parameter, String query, RowTypeInfo rowTypeInfo) {
        return JdbcInputFormat.buildJdbcInputFormat()
                .setDrivername(parameter.getRequired("pg.driverName"))
                .setUsername(parameter.getRequired("pg.username"))
                .setPassword(parameter.getRequired("pg.pwd"))
                .setDBUrl(parameter.getRequired("pg.url"))
                .setQuery(query)
                .setRowTypeInfo(rowTypeInfo)
                .finish();
    }

    public static JdbcRowOutputFormat outputToClickhouse(ParameterTool parameter, String query, int[] sqlTypes) {
        return JdbcRowOutputFormat.buildJdbcOutputFormat()
                .setDrivername(parameter.getRequired("ck.driverName"))
                .setDBUrl(parameter.getRequired("ck.url"))
                .setUsername(parameter.getRequired("ck.username"))
                .setPassword(parameter.getRequired("ck.pwd"))
                .setQuery(query)
                .setSqlTypes(sqlTypes)
                .finish();
    }

}
