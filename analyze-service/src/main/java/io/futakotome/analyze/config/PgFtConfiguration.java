package io.futakotome.analyze.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Objects;

@Configuration
public class PgFtConfiguration {
    @Bean(name = "futuDataSource")
    public DataSource dataSource(PgFtConfigurationProperties properties) throws SQLException {
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

    @Bean(name = "futuNamedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier("futuDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
