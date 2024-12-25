package io.futakotome.analyze.job;

import io.futakotome.analyze.biz.KLine;
import io.futakotome.analyze.mapper.KLineMapper;
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
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
public class KLineRepeatJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(KLineRepeatJob.class);
    private final KLine kLine;

    public KLineRepeatJob(KLineMapper kLineMapper) {
        this.kLine = new KLine(kLineMapper);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap params = jobExecutionContext.getJobDetail().getJobDataMap();
        String tableName = params.getString("table");
        String updateTimeStart;
        String updateTimeEnd;
        if (Objects.nonNull(params.getString("updateTimeStart")) &&
                !params.getString("updateTimeStart").isEmpty()) {
            updateTimeStart = params.getString("updateTimeStart");
        } else {
            LocalDate now = LocalDate.now();
            LocalTime morning = LocalTime.of(9, 0);
            updateTimeStart = LocalDateTime.of(now, morning).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        if (Objects.nonNull(params.getString("updateTimeEnd")) &&
                !params.getString("updateTimeEnd").isEmpty()) {
            updateTimeEnd = params.getString("updateTimeEnd");
        } else {
            LocalDate now = LocalDate.now();
            LocalTime afternoon = LocalTime.of(16, 0);
            updateTimeEnd = LocalDateTime.of(now, afternoon).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        LOGGER.info("检查时段:{}-{}", updateTimeStart, updateTimeEnd);
        kLine.kLinesRepeat(updateTimeStart, updateTimeEnd, tableName).forEach(kLineRepeatResponse -> {
            LOGGER.info(kLineRepeatResponse.toString());
        });
    }
}
