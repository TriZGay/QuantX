package io.futakotome.rtck.config;

import com.clickhouse.jdbc.ClickHouseDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

@Configuration
public class CkConfiguration {
    @Bean
    public ClickHouseDataSource dataSource(CkConfigurationProperties properties) throws SQLException {
        Objects.requireNonNull(properties.getUser());
        Objects.requireNonNull(properties.getPassword());
        Properties auth = new Properties();
        auth.put("user", properties.getUser());
        auth.put("password", properties.getPassword());
        return new ClickHouseDataSource(properties.getUrl(),auth);
    }
}
