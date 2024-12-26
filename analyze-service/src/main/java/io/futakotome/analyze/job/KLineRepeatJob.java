package io.futakotome.analyze.job;

import io.futakotome.analyze.biz.DataQuality;
import io.futakotome.analyze.biz.KLine;
import io.futakotome.analyze.controller.vo.KLineRepeatResponse;
import io.futakotome.analyze.mapper.DataQualityMapper;
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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Component
public class KLineRepeatJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(KLineRepeatJob.class);
    private final DataQuality dataQuality;

    public KLineRepeatJob(KLineMapper kLineMapper, DataQualityMapper dataQualityMapper) {
        KLine kLine = new KLine(kLineMapper);
        this.dataQuality = new DataQuality(dataQualityMapper, kLine);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap params = jobExecutionContext.getJobDetail().getJobDataMap();
        String tableName = params.getString("table");
        String startDate = params.getString("startDate");
        String endDate = params.getString("endDate");

        if (Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
            List<String> dateRanges = DateUtils.splitDateByDay(
                    LocalDate.parse(startDate, DateUtils.DATE_FORMATTER),
                    LocalDate.parse(endDate, DateUtils.DATE_FORMATTER)
            );
            dateRanges.forEach(today -> {
                LocalDate now = LocalDate.parse(today, DateUtils.DATE_FORMATTER);
                dataQuality.checkKlineRepeat(now, tableName);
            });
        } else {
            LocalDate now = LocalDate.now();
            dataQuality.checkKlineRepeat(now, tableName);
        }
    }
}
