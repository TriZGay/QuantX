package io.futakotome.quantx.source;

import io.futakotome.quantx.domain.Ema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.connector.jdbc.core.datastream.source.JdbcSource;

public class EmaSource {
    private static final String selectLatestEmqSql = "select market,\n" +
            "       code,\n" +
            "       rehab_type,\n" +
            "       ema_5,\n" +
            "       ema_10,\n" +
            "       ema_12,\n" +
            "       ema_20,\n" +
            "       ema_26,\n" +
            "       ema_60,\n" +
            "       ema_120,\n" +
            "       toString(update_time) as update_time\n" +
            "from t_ema_min_1_arc e\n" +
            "         inner join\n" +
            "     (select code, rehab_type, max(update_time) as update_time\n" +
            "      from t_ema_min_1_arc\n" +
            "      group by (code, rehab_type)) e2\n" +
            "     on e.code = e2.code and e.rehab_type = e2.rehab_type and e.update_time = e2.update_time";

    public static JdbcSource<Ema.EmaDto> selectMin1Latest(ParameterTool configs) {
        return JdbcSource.<Ema.EmaDto>builder()
                .setTypeInformation(TypeInformation.of(Ema.EmaDto.class))
                .setSql(selectLatestEmqSql)
                .setResultExtractor(resultSet -> new Ema.EmaDto(resultSet.getInt("market"),
                        resultSet.getString("code"),
                        resultSet.getInt("rehab_type"),
                        resultSet.getDouble("ema_5"),
                        resultSet.getDouble("ema_10"),
                        resultSet.getDouble("ema_12"),
                        resultSet.getDouble("ema_20"),
                        resultSet.getDouble("ema_26"),
                        resultSet.getDouble("ema_60"),
                        resultSet.getDouble("ema_120"),
                        resultSet.getString("update_time")))
                .setDBUrl(configs.getRequired("ck.url"))
                .setDriverName(configs.getRequired("ck.driverName"))
                .setUsername(configs.getRequired("ck.username"))
                .setPassword(configs.getRequired("ck.pwd"))
                .build();
    }

}
