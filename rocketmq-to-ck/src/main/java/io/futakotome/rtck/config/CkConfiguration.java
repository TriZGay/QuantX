package io.futakotome.rtck.config;

import com.clickhouse.jdbc.ClickHouseDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class CkConfiguration {
    @Bean
    public ClickHouseDataSource dataSource(CkConfigurationProperties properties) throws SQLException {
        return new ClickHouseDataSource(properties.getUrl());
    }
}
