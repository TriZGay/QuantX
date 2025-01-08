package io.futakotome.analyze.controller.vo;

import io.futakotome.analyze.utils.EntityToJobDataMapConverter;
import org.quartz.JobDataMap;

import javax.validation.constraints.NotNull;

public class JobRequest {
    @NotNull(message = "jobName必填")
    private String jobName;
    private String jobGroup;
    private String triggerName;
    private String triggerGroup;
    private String cron;

    @NotNull(message = "jobType必填")
    private JobType jobType;

    public static JobDataMap toJobDataMap(Object obj) {
        return EntityToJobDataMapConverter.convert(obj);
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }
}
