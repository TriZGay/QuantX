package io.futakotome.quantx.operators;

import io.futakotome.quantx.dto.TradeDateDto;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.types.Row;

import java.time.LocalDate;

public class TradeDateOperators {
    public static RowTypeInfo tradeDateRowTypeInfo() {
        return new RowTypeInfo(
                BasicTypeInfo.LONG_TYPE_INFO,
                BasicTypeInfo.STRING_TYPE_INFO,
                BasicTypeInfo.STRING_TYPE_INFO,
                BasicTypeInfo.INT_TYPE_INFO
        );
    }

    public static class ToTradeDatePojo implements MapFunction<Row, TradeDateDto> {
        @Override
        public TradeDateDto map(Row row) throws Exception {
            return new TradeDateDto(
                    row.getFieldAs(0),
                    row.getFieldAs(1),
                    row.getFieldAs(2),
                    row.getFieldAs(3)
            );
        }
    }

    public static class Between implements FilterFunction<TradeDateDto> {
        private final LocalDate startDate;
        private final LocalDate endDate;

        public Between(String startDate, String endDate) {
            this.startDate = LocalDate.parse(startDate);
            this.endDate = LocalDate.parse(endDate);
        }

        @Override
        public boolean filter(TradeDateDto tradeDateDto) throws Exception {
            LocalDate time = LocalDate.parse(tradeDateDto.getTime());
            return time.isAfter(startDate) && time.isBefore(endDate);
        }
    }
}
