package io.futakotome.analyze.job;

import io.futakotome.analyze.biz.MaN;
import io.futakotome.analyze.mapper.MaNMapper;
import io.futakotome.analyze.mapper.TradeDateMapper;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class KLineTransToMaJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(KLineTransToMaJob.class);
    private final MaN maN;

    public KLineTransToMaJob(MaNMapper mapper, TradeDateMapper tradeDateMapper) {
        this.maN = new MaN(mapper, tradeDateMapper);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap params = jobExecutionContext.getJobDetail().getJobDataMap();
        String fromTableName = params.getString("fromTableName");
        String toTableName = params.getString("toTableName");
        String startDateTime = params.getString("startDateTime");
        String endDateTime = params.getString("endDateTime");
        if (Objects.nonNull(startDateTime) && Objects.nonNull(endDateTime)) {
            maN.klineTransToMa(toTableName, fromTableName, startDateTime, endDateTime);
        } else {

        }
    }
}
