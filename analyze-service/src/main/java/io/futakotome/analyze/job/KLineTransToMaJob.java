package io.futakotome.analyze.job;

import io.futakotome.analyze.biz.MaN;
import io.futakotome.analyze.mapper.MaNMapper;
import io.futakotome.analyze.mapper.TradeDateMapper;
import io.futakotome.analyze.utils.DateUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Component
public class KLineTransToMaJob implements Job {
    private final MaN maN;

    public KLineTransToMaJob(MaNMapper mapper, TradeDateMapper tradeDateMapper) {
        this.maN = new MaN(mapper, tradeDateMapper);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap params = jobExecutionContext.getJobDetail().getJobDataMap();
        String fromTableName = params.getString("fromTableName");
        String toTableName = params.getString("toTableName");
        String startDateTime = params.getString("startDateTime");
        String endDateTime = params.getString("endDateTime");
        if (Objects.nonNull(startDateTime) && Objects.nonNull(endDateTime)) {
            maN.klineTransToMa(toTableName, fromTableName, startDateTime, endDateTime);
        } else {
            LocalDate now = LocalDate.now();
            LocalTime morning = LocalTime.of(9, 0);
            LocalTime afternoon = LocalTime.of(16, 0);
            String todayStartTime = LocalDateTime.of(now, morning).format(DateUtils.DATE_TIME_FORMATTER);
            String todayEndTime = LocalDateTime.of(now, afternoon).format(DateUtils.DATE_TIME_FORMATTER);
            maN.klineTransToMa(toTableName, fromTableName, todayStartTime, todayEndTime);
        }
    }
}
