package io.futakotome.quantx;

import io.futakotome.quantx.jobs.BatchDayKMA10Job;
import io.futakotome.quantx.jobs.BatchDayKMA20Job;
import io.futakotome.quantx.jobs.BatchDayKMA30Job;
import io.futakotome.quantx.jobs.BatchDayKMA5Job;
import io.futakotome.quantx.utils.Common;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;

public class QuantXMainJob {

    public static void main(String[] args) throws Exception {
        ParameterTool configs = ParameterTool.fromPropertiesFile(QuantXMainJob.class.getResourceAsStream("/base_config.properties"));
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        ParameterTool commandLieParameter = ParameterTool.fromArgs(args);
        String type = commandLieParameter.get("type", Common.JOB_TYPE_DAYK_MA30);
        switch (type) {
            case Common.JOB_TYPE_DAYK_MA5: {
                new BatchDayKMA5Job(env, configs, args).run();
                return;
            }
            case Common.JOB_TYPE_DAYK_MA10: {
                new BatchDayKMA10Job(env, configs, args).run();
                return;
            }
            case Common.JOB_TYPE_DAYK_MA20: {
                new BatchDayKMA20Job(env, configs, args).run();
                return;
            }
            case Common.JOB_TYPE_DAYK_MA30: {
                new BatchDayKMA30Job(env,configs,args).run();
                return;
            }
        }
    }
}
