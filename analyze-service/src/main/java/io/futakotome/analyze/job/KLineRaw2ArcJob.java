package io.futakotome.analyze.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class KLineRaw2ArcJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(KLineRaw2ArcJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.info("jobName:{}", jobExecutionContext.getJobDetail().getKey().getName());
        LOGGER.info("jobGroup:{}", jobExecutionContext.getJobDetail().getKey().getGroup());
        LOGGER.info("triggerName:{}", jobExecutionContext.getTrigger().getKey().getName());
        LOGGER.info("triggerGroup:{}", jobExecutionContext.getTrigger().getKey().getGroup());
        LOGGER.info("上次触发时间:{}", jobExecutionContext.getPreviousFireTime());
        LOGGER.info("本次触发时间:{}", jobExecutionContext.getFireTime());
        LOGGER.info("下次触发时间:{}", jobExecutionContext.getNextFireTime());
        LOGGER.info("调度时间:{}", jobExecutionContext.getScheduledFireTime());
    }
}
