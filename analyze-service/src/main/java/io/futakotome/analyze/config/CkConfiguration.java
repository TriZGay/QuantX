package io.futakotome.analyze.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Objects;

@Configuration
public class CkConfiguration {
    @Bean
    public DataSource dataSource(CkConfigurationProperties properties) throws SQLException {
        Objects.requireNonNull(properties.getUser());
        Objects.requireNonNull(properties.getPassword());
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getUrl());
        config.setUsername(properties.getUser());
        config.setPassword(properties.getPassword());
        config.setMinimumIdle(10);
        config.setMaximumPoolSize(20);
        config.setDriverClassName(properties.getDriverClass());
        return new HikariDataSource(config);
    }
}
