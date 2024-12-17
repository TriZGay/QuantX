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
public class KLineRaw2ArcJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(KLineRaw2ArcJob.class);
    private final KLine kLine;

    public KLineRaw2ArcJob(KLineMapper kLineMapper) {
        this.kLine = new KLine(kLineMapper);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        LOGGER.info("jobDataMap:updateTimeStart,{}", jobExecutionContext.getJobDetail().getJobDataMap().getString("updateTimeStart"));
//        LOGGER.info("jobDataMap:updateTimeEnd,{}", jobExecutionContext.getJobDetail().getJobDataMap().getString("updateTimeEnd"));
//        LOGGER.info("jobDataMap:fromTable,{}", jobExecutionContext.getJobDetail().getJobDataMap().getString("fromTable"));
//        LOGGER.info("jobDataMap:toTable,{}", jobExecutionContext.getJobDetail().getJobDataMap().getString("toTable"));
//        LOGGER.info("本次触发时间:{}", jobExecutionContext.getFireTime());
        String updateTimeStart;
        String updateTimeEnd;
        JobDataMap params = jobExecutionContext.getJobDetail().getJobDataMap();
        if (Objects.nonNull(params.getString("updateTimeStart")) &&
                !params.getString("updateTimeStart").isEmpty()) {
            updateTimeStart = params.getString("updateTimeStart");
        } else {
            LocalDate now = LocalDate.now();
            LocalTime morning = LocalTime.of(9, 30);
            updateTimeStart = LocalDateTime.of(now, morning).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        if (Objects.nonNull(params.getString("updateTimeEnd")) &&
                !params.getString("updateTimeEnd").isEmpty()) {
            updateTimeEnd = params.getString("updateTimeEnd");
        } else {
            LocalDate now = LocalDate.now();
            LocalTime afternoon = LocalTime.of(15, 0);
            updateTimeEnd = LocalDateTime.of(now, afternoon).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        int resultRowNum = kLine.kLinesArchive(params.getString("fromTable"), params.getString("toTable"),
                updateTimeStart, updateTimeEnd);
        LOGGER.info("归档行数:{}", resultRowNum);
    }
}
