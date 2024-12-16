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

@Component
public class KLineRaw2ArcJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(KLineRaw2ArcJob.class);
    private final KLine kLine;

    public KLineRaw2ArcJob(KLineMapper kLineMapper) {
        this.kLine = new KLine(kLineMapper);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.info("jobDataMap:updateTimeStart,{}", jobExecutionContext.getJobDetail().getJobDataMap().getString("updateTimeStart"));
        LOGGER.info("jobDataMap:updateTimeEnd,{}", jobExecutionContext.getJobDetail().getJobDataMap().getString("updateTimeEnd"));
        LOGGER.info("jobDataMap:fromTable,{}", jobExecutionContext.getJobDetail().getJobDataMap().getString("fromTable"));
        LOGGER.info("jobDataMap:toTable,{}", jobExecutionContext.getJobDetail().getJobDataMap().getString("toTable"));
        LOGGER.info("本次触发时间:{}", jobExecutionContext.getFireTime());
        JobDataMap params = jobExecutionContext.getJobDetail().getJobDataMap();
        int resultRowNum = kLine.kLinesArchive(params.getString("fromTable"), params.getString("toTable"),
                params.getString("updateTimeStart"), params.getString("updateTimeEnd"));
        LOGGER.info("行数:{}", resultRowNum);
    }
}
