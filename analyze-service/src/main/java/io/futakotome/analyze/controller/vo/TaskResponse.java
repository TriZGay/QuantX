package io.futakotome.analyze.controller.vo;

import java.util.Date;

public class TaskResponse {
    private String jobGroup;
    private String jobName;
    private String triggerName;
    private String triggerGroup;
    private String cron;
    private String state;
    private String jobDataMap;
    private Date nextFireTime;

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
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

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getJobDataMap() {
        return jobDataMap;
    }

    public void setJobDataMap(String jobDataMap) {
        this.jobDataMap = jobDataMap;
    }

    public Date getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(Date nextFireTime) {
        this.nextFireTime = nextFireTime;
    }
}
