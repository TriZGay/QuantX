package io.futakotome.analyze.controller.vo;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.futakotome.analyze.utils.EntityToJobDataMapConverter;
import org.quartz.JobDataMap;

import javax.validation.constraints.NotNull;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "jobType", visible = true)
@JsonSubTypes(value = {
        @JsonSubTypes.Type(value = KLineRaw2ArcJobRequest.class, name = "KLINE_RAW_TO_ARC"),
        @JsonSubTypes.Type(value = KLineRepeatCheckJobRequest.class, name = "KLINE_REPEAT_CHECK"),
        @JsonSubTypes.Type(value = KLineTransToMaJobRequest.class, name = "KLINE_ARC_TO_MA"),
        @JsonSubTypes.Type(value = KLineTransToBollJobRequest.class, name = "KLINE_ARC_TO_BOLL"),
        @JsonSubTypes.Type(value = KLineTransToEmaJobRequest.class, name = "KLINE_ARC_TO_EMA"),
        @JsonSubTypes.Type(value = KLineTransToMacdJobRequest.class, name = "KLINE_ARC_TO_MACD"),
        @JsonSubTypes.Type(value = KLineTransToRsiJobRequest.class, name = "KLINE_ARC_TO_RSI"),
        @JsonSubTypes.Type(value = KLineTransToKdjJobRequest.class, name = "KLINE_ARC_TO_KDJ")
})
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
