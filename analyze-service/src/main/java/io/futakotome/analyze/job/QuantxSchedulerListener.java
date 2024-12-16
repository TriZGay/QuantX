package io.futakotome.analyze.job;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class QuantxSchedulerListener implements SchedulerListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuantxSchedulerListener.class);

    @Override
    public void jobScheduled(Trigger trigger) {
        LOGGER.info("jobScheduled:{}", trigger.getKey());
    }

    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        LOGGER.info("jobUnscheduled:{}", triggerKey);
    }

    @Override
    public void triggerFinalized(Trigger trigger) {
        LOGGER.info("triggerFinalized:{}", trigger.getKey());
    }

    @Override
    public void triggerPaused(TriggerKey triggerKey) {
        LOGGER.info("triggerPaused:{}", triggerKey);
    }

    @Override
    public void triggersPaused(String s) {
        LOGGER.info("triggersPaused:{}", s);
    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {
        LOGGER.info("triggerResumed:{}", triggerKey);
    }

    @Override
    public void triggersResumed(String s) {
        LOGGER.info("triggersResumed:{}", s);
    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
        LOGGER.info("jobAdded:{}", jobDetail.getKey());
    }

    @Override
    public void jobDeleted(JobKey jobKey) {
        LOGGER.info("jobDeleted:{}", jobKey);
    }

    @Override
    public void jobPaused(JobKey jobKey) {
        LOGGER.info("jobPaused:{}", jobKey);
    }

    @Override
    public void jobsPaused(String s) {
        LOGGER.info("jobsPaused:{}", s);
    }

    @Override
    public void jobResumed(JobKey jobKey) {
        LOGGER.info("jobResumed:{}", jobKey);
    }

    @Override
    public void jobsResumed(String s) {
        LOGGER.info("jobsResumed:{}", s);
    }

    @Override
    public void schedulerError(String s, SchedulerException e) {
        LOGGER.info("schedulerError:{}", s, e);
    }

    @Override
    public void schedulerInStandbyMode() {

    }

    @Override
    public void schedulerStarted() {
        LOGGER.info("schedulerStarted");
    }

    @Override
    public void schedulerStarting() {
        LOGGER.info("schedulerStarting");
    }

    @Override
    public void schedulerShutdown() {
        LOGGER.info("schedulerShutdown");
    }

    @Override
    public void schedulerShuttingdown() {
        LOGGER.info("schedulerShuttingdown");
    }

    @Override
    public void schedulingDataCleared() {
        LOGGER.info("schedulingDataCleared");
    }
}
