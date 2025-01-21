package io.futakotome.analyze.job;

import io.futakotome.analyze.biz.KLine;
import io.futakotome.analyze.mapper.KLineMapper;
import io.futakotome.analyze.utils.DateUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Component
public class KLineRaw2ArcJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(KLineRaw2ArcJob.class);
    private final KLine kLine;

    public KLineRaw2ArcJob(KLineMapper kLineMapper) {
        this.kLine = new KLine(kLineMapper);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String updateTimeStart;
        String updateTimeEnd;
        JobDataMap params = jobExecutionContext.getJobDetail().getJobDataMap();
        if (Objects.nonNull(params.getString("updateTimeStart")) &&
                !params.getString("updateTimeStart").isEmpty()) {
            updateTimeStart = params.getString("updateTimeStart");
        } else {
            LocalDate now = LocalDate.now();
            LocalTime morning = LocalTime.of(9, 0);
            updateTimeStart = LocalDateTime.of(now, morning).format(DateUtils.DATE_TIME_FORMATTER);
        }
        if (Objects.nonNull(params.getString("updateTimeEnd")) &&
                !params.getString("updateTimeEnd").isEmpty()) {
            updateTimeEnd = params.getString("updateTimeEnd");
        } else {
            LocalDate now = LocalDate.now();
            LocalTime afternoon = LocalTime.of(16, 0);
            updateTimeEnd = LocalDateTime.of(now, afternoon).format(DateUtils.DATE_TIME_FORMATTER);
        }
        kLine.kLinesArchive(params.getString("fromTable"), params.getString("toTable"), updateTimeStart, updateTimeEnd);
    }
}
