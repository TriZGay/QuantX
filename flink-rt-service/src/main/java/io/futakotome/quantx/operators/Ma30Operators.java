package io.futakotome.quantx.operators;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple15;
import org.apache.flink.api.java.tuple.Tuple6;
import org.apache.flink.types.Row;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ma30Operators {
    public static class ClosePriceSum implements ReduceFunction<Tuple15<Integer, String, Integer, Double, Double, Double, Double, Double,
            Long, Double, Double, Double, Double, LocalDateTime, LocalDateTime>> {
        @Override
        public Tuple15<Integer, String, Integer, Double, Double, Double, Double, Double, Long, Double, Double, Double, Double, LocalDateTime, LocalDateTime> reduce(Tuple15<Integer, String, Integer, Double, Double, Double, Double, Double, Long, Double, Double, Double, Double, LocalDateTime, LocalDateTime> v1,
                                                                                                                                                                    Tuple15<Integer, String, Integer, Double, Double, Double, Double, Double, Long, Double, Double, Double, Double, LocalDateTime, LocalDateTime> v2) throws Exception {
            return Tuple15.of(v1.f0, v1.f1, v1.f2, v1.f3, v1.f4, v1.f5, v1.f6 + v2.f6, v1.f7, v1.f8, v1.f9, v1.f10, v1.f11, v1.f12, v2.f13, v1.f14);
        }
    }

    public static class ClosePriceAvg implements MapFunction<Tuple15<Integer, String, Integer, Double, Double, Double, Double, Double, Long, Double, Double, Double, Double, LocalDateTime, LocalDateTime>, Tuple6<Integer, String, Integer, Double, LocalDateTime, LocalDateTime>> {
        @Override
        public Tuple6<Integer, String, Integer, Double, LocalDateTime, LocalDateTime> map(Tuple15<Integer, String, Integer, Double, Double, Double, Double, Double, Long, Double, Double, Double, Double, LocalDateTime, LocalDateTime> v1) throws Exception {
            return Tuple6.of(v1.f0, v1.f1, v1.f2, v1.f6 / 30, v1.f13, LocalDateTime.now());
        }
    }

    public static class ToRow implements MapFunction<Tuple6<Integer, String, Integer, Double, LocalDateTime, LocalDateTime>, Row> {
        @Override
        public Row map(Tuple6<Integer, String, Integer, Double, LocalDateTime, LocalDateTime> ma30) throws Exception {
            return Row.of(ma30.f0, ma30.f1, ma30.f2, ma30.f3, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(ma30.f4), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(ma30.f5));
        }
    }

    public static int[] ma30Types() {
        return new int[]{java.sql.Types.INTEGER, java.sql.Types.NCHAR, java.sql.Types.INTEGER, java.sql.Types.DOUBLE, java.sql.Types.NCHAR, java.sql.Types.NCHAR};
    }

    public static TypeInformation<Tuple6<Integer, String, Integer, Double, LocalDateTime, LocalDateTime>> ma30TypeInformation() {
        return Types.TUPLE(Types.INT, Types.STRING, Types.INT, Types.DOUBLE, Types.LOCAL_DATE_TIME, Types.LOCAL_DATE_TIME);
    }
}
