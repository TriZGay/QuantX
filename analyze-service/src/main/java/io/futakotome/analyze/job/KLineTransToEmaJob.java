package io.futakotome.analyze.job;

import io.futakotome.analyze.biz.Ema;
import io.futakotome.analyze.mapper.EmaMapper;
import io.futakotome.analyze.mapper.KLineMapper;
import io.futakotome.analyze.mapper.MaNMapper;
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
public class KLineTransToEmaJob implements Job {
    private final Ema ema;

    public KLineTransToEmaJob(EmaMapper emaMapper, KLineMapper kLineMapper, MaNMapper maNMapper) {
        this.ema = new Ema(emaMapper, kLineMapper, maNMapper);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap params = jobExecutionContext.getJobDetail().getJobDataMap();
        String kTableName = params.getString("kTableName");
        String maTableName = params.getString("maTableName");
        String toTableName = params.getString("toTableName");
        String startDateTime = params.getString("startDateTime");
        String endDateTime = params.getString("endDateTime");
        if (Objects.nonNull(startDateTime) && Objects.nonNull(endDateTime)) {
            ema.calculate(toTableName, kTableName, maTableName, startDateTime, endDateTime);
        } else {
            LocalDate now = LocalDate.now();
            LocalTime morning = LocalTime.of(9, 0);
            LocalTime afternoon = LocalTime.of(16, 0);
            String todayStartTime = LocalDateTime.of(now, morning).format(DateUtils.DATE_TIME_FORMATTER);
            String todayEndTime = LocalDateTime.of(now, afternoon).format(DateUtils.DATE_TIME_FORMATTER);
            ema.calculate(toTableName, kTableName, maTableName, todayStartTime, todayEndTime);
        }
    }
}
