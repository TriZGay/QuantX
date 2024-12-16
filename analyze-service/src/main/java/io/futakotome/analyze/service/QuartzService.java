package io.futakotome.analyze.service;

import io.futakotome.analyze.job.QuantxJobListener;
import io.futakotome.analyze.job.QuantxSchedulerListener;
import io.futakotome.analyze.job.QuantxTriggerListener;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
public class QuartzService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzService.class);
    private static final String DEFAULT_JOB_GROUP = "default_job_group";
    private static final String DEFAULT_TRIGGER_GROUP = "default_trigger_group";
    private static final String TRIGGER_PREFIX = "TRIGGER_";

    private final Scheduler scheduler;

    public QuartzService(Scheduler scheduler, QuantxSchedulerListener schedulerListener, QuantxJobListener jobListener, QuantxTriggerListener triggerListener) throws SchedulerException {
        this.scheduler = scheduler;
        this.scheduler.getListenerManager().addSchedulerListener(schedulerListener);
        this.scheduler.getListenerManager().addJobListener(jobListener);
        this.scheduler.getListenerManager().addTriggerListener(triggerListener);
    }

    public String addJob(String jobName, String jobGroup, String triggerName, String triggerGroup, String cron, JobDataMap dataMap, Class<? extends Job> jobClass) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName,
                    Objects.nonNull(jobGroup) ? jobGroup : DEFAULT_JOB_GROUP);
            if (scheduler.checkExists(jobKey)) {
                LOGGER.warn("添加任务失败,已存在该任务,jobKey为:{}", jobKey);
                throw new RuntimeException("已存在该任务");
            }
            JobDetail job = JobBuilder.newJob()
                    .ofType(jobClass)
                    .withIdentity(jobKey)
                    .usingJobData(dataMap)
                    .build();
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger()
                    .withIdentity(TriggerKey.triggerKey(
                            Objects.nonNull(triggerName) ? triggerName : TRIGGER_PREFIX + jobName,
                            Objects.nonNull(triggerGroup) ? triggerGroup : DEFAULT_TRIGGER_GROUP));
            Trigger trigger;
            if (cron == null) {
                trigger = triggerBuilder.build();
            } else {
                CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
                trigger = triggerBuilder.withSchedule(cronScheduleBuilder)
                        .build();
            }
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
            return "添加任务成功";

        } catch (Exception e) {
            LOGGER.error("添加任务失败.", e);
            throw new RuntimeException("添加任务失败");
        }
    }

    public String addJob(String jobName, String cron, JobDataMap dataMap, Class<? extends Job> jobClass) {
        return addJob(jobName, null, null, null, cron, dataMap, jobClass);
    }

    public String addJob(String jobName, String cron, Class<? extends Job> jobClass) {
        return addJob(jobName, null, null, null, cron, new JobDataMap(), jobClass);
    }

    public String addJob(String jobName, JobDataMap dataMap, Class<? extends Job> jobClass) {
        return addJob(jobName, null, null, null, null, dataMap, jobClass);
    }

    public String addJob(String jobName, Class<? extends Job> jobClass) {
        return addJob(jobName, null, null, null, null, new JobDataMap(), jobClass);
    }

    public String delJob(String jobName) {
        return delJob(jobName, null, null, null);
    }

    public String delJob(String jobName, String jobGroup, String triggerName, String triggerGroup) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName,
                    Objects.nonNull(jobGroup) ? jobGroup : DEFAULT_JOB_GROUP);
            TriggerKey triggerKey = TriggerKey.triggerKey(
                    Objects.nonNull(triggerName) ? triggerName : TRIGGER_PREFIX + jobName,
                    Objects.nonNull(triggerGroup) ? triggerGroup : DEFAULT_TRIGGER_GROUP);
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if (Objects.isNull(trigger)) {
                LOGGER.warn("删除任务失败,任务不存在.{},{}", jobKey, triggerKey);
                return "删除任务失败,任务不存在";
            }
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(jobKey);
            return "删除任务成功";
        } catch (Exception e) {
            LOGGER.error("删除任务失败.", e);
            return "删除任务失败";
        }
    }

    public String getTriggers() {
        try {
            Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.anyGroup());
            for (JobKey jobKey : jobKeys) {
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                LOGGER.info("job:{}", jobDetail);
//                List<? extends Trigger> triggersOfJob = scheduler.getTriggersOfJob(jobKey);
//                for (Trigger trigger : triggersOfJob) {
//                    Trigger.TriggerState state = scheduler.getTriggerState(trigger.getKey());
//                }
            }
//            jobKeys.stream().flatMap(jobKey -> {
//                        try {
//                            return scheduler.getTriggersOfJob(jobKey);
//                        } catch (SchedulerException e) {
//                            throw new RuntimeException(e);
//                        }
//                    })

//            scheduler.getJobKeys(GroupMatcher.anyGroup());
            LOGGER.info(scheduler.getMetaData().toString());
            return scheduler.getMetaData().toString();
        } catch (Exception e) {
            LOGGER.error("查询任务失败.", e);
            return "fail";
        }
    }
}
