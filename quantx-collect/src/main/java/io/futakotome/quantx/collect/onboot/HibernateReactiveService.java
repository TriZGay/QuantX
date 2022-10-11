package io.futakotome.quantx.collect.onboot;

import io.futakotome.quantx.collect.tenant.TenantDependentPool;
import io.quarkus.runtime.StartupEvent;
import org.hibernate.reactive.pool.ReactiveConnectionPool;
import org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder;
import org.hibernate.reactive.provider.Settings;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class HibernateReactiveService {
    private static final Logger LOGGER = Logger.getLogger(HibernateReactiveService.class.getName());

    public void initHibernateReactiveEnvironment(@Observes StartupEvent startupEvent) {
        ReactiveServiceRegistryBuilder builder = new ReactiveServiceRegistryBuilder();
        LOGGER.infov("Hibernate Reactive Setting {0} set up: {1}", Settings.SQL_CLIENT_POOL, TenantDependentPool.class.getName());
        builder.addService(ReactiveConnectionPool.class, new TenantDependentPool()).build();
        LOGGER.infov("ReactiveConnectionPool service : {0}", builder.getBootstrapServiceRegistry().getService(ReactiveConnectionPool.class));
    }
}
