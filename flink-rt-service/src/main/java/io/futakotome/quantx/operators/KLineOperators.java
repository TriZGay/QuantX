package io.futakotome.quantx.operators;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple15;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.types.Row;

import java.time.LocalDateTime;

public class KLineOperators {

    public static RowTypeInfo klineRowTypeInfo() {
        return new RowTypeInfo(
                BasicTypeInfo.BYTE_TYPE_INFO,  //market f0
                BasicTypeInfo.STRING_TYPE_INFO, //code f1
                BasicTypeInfo.BYTE_TYPE_INFO, //rehabType f2
                BasicTypeInfo.DOUBLE_TYPE_INFO, //highPrice f3
                BasicTypeInfo.DOUBLE_TYPE_INFO, //openPrice f4
                BasicTypeInfo.DOUBLE_TYPE_INFO, //lowPrice f5
                BasicTypeInfo.DOUBLE_TYPE_INFO, //closePrice f6
                BasicTypeInfo.DOUBLE_TYPE_INFO, //lastClosePrice f7
                BasicTypeInfo.LONG_TYPE_INFO, //volume f8
                BasicTypeInfo.DOUBLE_TYPE_INFO, //turnover f9
                BasicTypeInfo.DOUBLE_TYPE_INFO, //turnoverRate f10
                BasicTypeInfo.DOUBLE_TYPE_INFO, //pe f11
                BasicTypeInfo.DOUBLE_TYPE_INFO, //changeRate f12
                BasicTypeInfo.of(LocalDateTime.class), //updateTime f13
                BasicTypeInfo.of(LocalDateTime.class) //addTime f14
        );
    }

    public static TypeInformation<Tuple15<Integer, String, Integer, Double, Double, Double, Double, Double,
            Long, Double, Double, Double, Double, LocalDateTime, LocalDateTime>> klineTypeInformation() {
        return Types.TUPLE(Types.INT, Types.STRING, Types.INT, Types.DOUBLE, Types.DOUBLE, Types.DOUBLE, Types.DOUBLE, Types.DOUBLE,
                Types.LONG, Types.DOUBLE, Types.DOUBLE, Types.DOUBLE, Types.DOUBLE, Types.LOCAL_DATE_TIME, Types.LOCAL_DATE_TIME);
    }

    public static class ToTuple15 implements MapFunction<Row, Tuple15<Integer, String, Integer, Double, Double, Double, Double, Double,
            Long, Double, Double, Double, Double, LocalDateTime, LocalDateTime>> {

        @Override
        public Tuple15<Integer, String, Integer, Double, Double, Double, Double, Double, Long, Double, Double, Double, Double, LocalDateTime, LocalDateTime> map(Row row) throws Exception {
            return Tuple15.of(
                    Byte.toUnsignedInt(row.getFieldAs(0)),
                    row.getFieldAs(1),
                    Byte.toUnsignedInt(row.getFieldAs(2)),
                    row.getFieldAs(3),
                    row.getFieldAs(4),
                    row.getFieldAs(5),
                    row.getFieldAs(6),
                    row.getFieldAs(7),
                    row.getFieldAs(8),
                    row.getFieldAs(9),
                    row.getFieldAs(10),
                    row.getFieldAs(11),
                    row.getFieldAs(12),
                    row.getFieldAs(13),
                    row.getFieldAs(14)
            );
        }
    }

    public static class FilterByRehabType implements FilterFunction<Tuple15<Integer, String, Integer, Double, Double, Double, Double, Double,
            Long, Double, Double, Double, Double, LocalDateTime, LocalDateTime>> {
        private final Integer rehabType;

        public FilterByRehabType(Integer rehabType) {
            this.rehabType = rehabType;
        }

        @Override
        public boolean filter(Tuple15<Integer, String, Integer, Double, Double, Double, Double, Double, Long, Double, Double, Double, Double, LocalDateTime, LocalDateTime> tuple15) throws Exception {
            //f2:rehab_type
            return tuple15.f2.equals(rehabType);
        }
    }
}
