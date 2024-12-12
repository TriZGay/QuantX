package io.futakotome.analyze.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

//@Configuration
public class QuartzConfiguration {
//    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("offlineDataSource") DataSource dataSource) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setDataSource(dataSource);
        return schedulerFactoryBean;
    }
}
