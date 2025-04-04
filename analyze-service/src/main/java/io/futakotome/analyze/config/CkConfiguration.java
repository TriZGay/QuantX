package io.futakotome.analyze.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Objects;

@Configuration
public class CkConfiguration {

    @Bean(name = "analyzeDataSource")
    public DataSource dataSource(CkConfigurationProperties properties) throws SQLException {
        Objects.requireNonNull(properties.getUser());
        Objects.requireNonNull(properties.getPassword());
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getUrl());
        config.setUsername(properties.getUser());
        config.setPassword(properties.getPassword());
        config.setMinimumIdle(3);
        config.setMaximumPoolSize(20);
        config.setDriverClassName(properties.getDriverClass());
        return new HikariDataSource(config);
    }

    @Bean(name = "analyzeNamedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier("analyzeDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
