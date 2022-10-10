package io.futakotome.quantx.collect.onboot;

import io.agroal.api.AgroalDataSource;
import io.agroal.api.configuration.AgroalConnectionPoolConfiguration;
import io.agroal.api.configuration.AgroalDataSourceConfiguration;
import io.agroal.api.configuration.supplier.AgroalConnectionFactoryConfigurationSupplier;
import io.agroal.api.configuration.supplier.AgroalConnectionPoolConfigurationSupplier;
import io.agroal.api.configuration.supplier.AgroalDataSourceConfigurationSupplier;
import io.agroal.api.security.NamePrincipal;
import io.agroal.api.security.SimplePassword;
import io.quarkus.liquibase.LiquibaseFactory;
import io.quarkus.liquibase.runtime.LiquibaseConfig;
import io.quarkus.runtime.StartupEvent;
import liquibase.Liquibase;
import liquibase.changelog.ChangeSetStatus;
import liquibase.exception.LiquibaseException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class LiquibaseService {
    private static final Logger LOGGER = Logger.getLogger(LiquibaseService.class.getName());

    @ConfigProperty(name = "quarkus.datasource.reactive.url")
    String datasourceUrl;

    @ConfigProperty(name = "quarkus.datasource.username")
    String datasourceUsername;

    @ConfigProperty(name = "quarkus.datasource.password")
    String datasourcePwd;

    @ConfigProperty(name = "quarkus.liquibase.migrate-at-start")
    boolean migrateAtStart;

    public void runLiquibaseMigration(@Observes StartupEvent startupEvent) throws SQLException {
        String url = "jdbc:" + this.datasourceUrl.substring(15);
        AgroalDataSource dataSource = AgroalDataSource.from(
                new AgroalDataSourceConfigurationSupplier()
                        .connectionPoolConfiguration(
                                new AgroalConnectionPoolConfigurationSupplier()
                                        .connectionFactoryConfiguration(
                                                new AgroalConnectionFactoryConfigurationSupplier()
                                                        .jdbcUrl(url)
                                                        .autoCommit(false)
                                                        .principal(new NamePrincipal(this.datasourceUsername))
                                                        .credential(new SimplePassword(this.datasourcePwd))
                                        )
                                        .minSize(2)
                                        .maxSize(8)
                        )
                        .metricsEnabled(false)
                        .dataSourceImplementation(AgroalDataSourceConfiguration.DataSourceImplementation.AGROAL)
        );
        LiquibaseConfig liquibaseConfig = new LiquibaseConfig();
        liquibaseConfig.migrateAtStart = this.migrateAtStart;
        liquibaseConfig.changeLogParameters = new HashMap<>();
        LiquibaseFactory factory = new LiquibaseFactory(liquibaseConfig, dataSource, "default");
        try (Liquibase liquibase = factory.createLiquibase()) {
            LOGGER.info("Liquibase migration service start.");
            liquibase.validate();
            liquibase.update(factory.createContexts(), factory.createLabels());
            List<ChangeSetStatus> changeSetStatuses = liquibase.getChangeSetStatuses(factory.createContexts(), factory.createLabels());
            LOGGER.infov("Liquibase migration success.");
            changeSetStatuses.forEach(System.out::println);
        } catch (LiquibaseException e) {
            LOGGER.error("Liquibase migration failed.", e);
        }
    }
}
