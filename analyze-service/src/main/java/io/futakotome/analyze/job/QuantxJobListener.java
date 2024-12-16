package io.futakotome.analyze.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class QuantxJobListener implements JobListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuantxJobListener.class);

    @Override
    public String getName() {
        return "quantxJobListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        LOGGER.info("jobToBeExecuted:{}", jobExecutionContext.toString());
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
        LOGGER.info("jobExecutionVetoed:{}", jobExecutionContext.toString());
    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        LOGGER.info("jobWasExecuted:{}", jobExecutionContext.toString());
    }
}
